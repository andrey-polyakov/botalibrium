package botalibrium.service;

import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.entity.embedded.records.RecordedVariable;
import botalibrium.entity.embedded.records.SelectionNode;
import botalibrium.service.contract.CustomFieldsServiceContract;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomFieldsService implements CustomFieldsServiceContract {

    public static final String MISSING_REQUIRED_NODE = "Required node is missing, please fill that in";
    public static final String MUTUALLY_EXCLUSIVE_NODES = "Only one option must be chosen";

    private final BasicDAO<CustomFieldGroupDefinition, ObjectId> customFields;

    @Autowired
    public CustomFieldsService(BasicDAO<CustomFieldGroupDefinition, ObjectId> dao) {
        this.customFields = dao;
    }

    @Override
    public void save(CustomFieldGroupDefinition customFieldGroupDefinition) throws ValidationException {
        if (customFieldGroupDefinition.getApplicableEntities().isEmpty()) {
            throw new ValidationException("Field Definition must be applicable to at least one entity");
        }
        if (customFieldGroupDefinition.getSelectionNodes().isEmpty()) {
            throw new ValidationException("At least one Field Definition is required for every group");
        }
        if (customFieldGroupDefinition.getId() == null) {
            customFieldGroupDefinition.setId(new ObjectId());
        }
        customFields.save(customFieldGroupDefinition);
    }

    class Tuple {
        private final Iterator<Map.Entry<String, SelectionNode>> definitionIterator;
        private Map<String, SelectionNode> definition;
        private Map<String, SelectionNode> object;

        public Tuple(Map<String, SelectionNode> definition, Map<String, SelectionNode> object) {
            this.definition = definition;
            this.object = object;
            definitionIterator = definition.entrySet().iterator();
        }

        public Iterator<Map.Entry<String, SelectionNode>> getDefinitionIterator() {
            return definitionIterator;
        }

        public Map<String, SelectionNode> getDefinition() {
            return definition;
        }

        public Map<String, SelectionNode> getObject() {
            return object;
        }
    }

    /**
     * BFS validation of one tree against the other.
     *
     * @param group
     * @param entityClass
     * @throws ServiceException
     */
    @Override
    public void validate(CustomFieldGroup group, String entityClass) throws ServiceException {
        if (!group.getDefinition().getApplicableEntities().contains(entityClass)) {
            throw new ValidationException("Given Field Group is not compatible with given entity set").set("CLASS", entityClass);
        }
        if (group.getSelectionNodes() == null || group.getSelectionNodes().isEmpty()) {
            throw new ValidationException("At least one field is required for every group").set("CLASS", entityClass);
        }
        Deque<Tuple> stack = new LinkedList<>();
        stack.add(new Tuple(group.getDefinition().getSelectionNodes(), group.getSelectionNodes()));
        while (!stack.isEmpty()) {
            Tuple stackItem = stack.pop();
            while (stackItem.getDefinitionIterator().hasNext()) {
                Map.Entry<String, SelectionNode> definitionEntry = stackItem.getDefinitionIterator().next();
                SelectionNode definitionNode = definitionEntry.getValue();
                SelectionNode objectNode = stackItem.getObject().get(definitionEntry.getKey());
                if (objectNode == null) {
                    continue;
                }
                if (definitionNode.isNodeChoiceRequired() && (objectNode.getNodes() == null || objectNode.getNodes().isEmpty())) {
                    throw new ValidationException(MISSING_REQUIRED_NODE).set("MISSING_NODE", definitionNode).set("CLASS", entityClass);
                }
                knownNodesCheck(definitionNode, objectNode);
                knownVariablesCheck(definitionNode, objectNode, entityClass);
                if (definitionNode.isMutuallyExclusiveNodes() && objectNode.getNodes().size() > 1) {
                    throw new ValidationException(MUTUALLY_EXCLUSIVE_NODES).set("DEFINITION_NODE", definitionNode).set("CLASS", entityClass);
                }
                if (!definitionNode.getNodes().isEmpty()) {
                    stack.add(new Tuple(definitionNode.getNodes(), objectNode.getNodes()));
                }
            }
        }
    }

    private void knownVariablesCheck(SelectionNode definitionNode, SelectionNode objectNode, String entityClass) throws ServiceException {
        Set<String> unrecognizedVariables = new TreeSet<>(objectNode.getVariables().keySet());
        unrecognizedVariables.removeAll(definitionNode.getVariables().keySet());
        if (!unrecognizedVariables.isEmpty()) {
            throw new ValidationException("There are unrecognized variables: " + print(unrecognizedVariables)).
                    set("DEFINITION_NODE", definitionNode).
                    set("OBJECT_NODE", objectNode).set("CLASS", entityClass);
        }
        for (Map.Entry<String, RecordedVariable> definitionVariable : definitionNode.getVariables().entrySet()) {
            RecordedVariable rv = objectNode.getVariables().get(definitionVariable.getKey());
            if (rv == null) {
                if(definitionVariable.getValue().isRequired()) {
                    throw new ValidationException("Variable is missing").
                            set("MISSING_VARIABLE", definitionVariable.getKey()).
                            set("DEFINITION_NODE", definitionNode).
                            set("OBJECT_NODE", objectNode).set("CLASS", entityClass);
                }
            } else {
                if (!definitionVariable.getValue().getValues().isEmpty()) {
                    if (rv.getValues().size() != 1) {
                        throw new ValidationException("Exactly one value is expected").
                                set("INVALID_VARIABLE", rv).
                                set("DEFINITION_NODE", definitionNode).
                                set("OBJECT_NODE", objectNode).set("CLASS", entityClass);
                    }
                    if (!definitionVariable.getValue().getValues().containsAll(rv.getValues())) {
                        throw new ValidationException("Unexpected variable value").
                                set("EXPECTED_VALUES", definitionVariable.getValue().getValues()).
                                set("INVALID_VARIABLE", rv).
                                set("DEFINITION_NODE", definitionNode).
                                set("OBJECT_NODE", objectNode).set("CLASS", entityClass);
                    }

                }
            }
        }
    }

    private void knownNodesCheck(SelectionNode definitionNode, SelectionNode objectNode) throws ServiceException {
        Set<String> unrecognizedNodes = new TreeSet<>(objectNode.getNodes().keySet());
        unrecognizedNodes.removeAll(definitionNode.getNodes().keySet());
        if (!unrecognizedNodes.isEmpty()) {
            throw new ValidationException("There are unrecognized nodes: " + print(unrecognizedNodes)).
                    set("DEFINITION_NODE", definitionNode).
                    set("OBJECT_NODE", objectNode);
        }
        if (definitionNode.isNodeChoiceRequired() && objectNode.getNodes().isEmpty()) {
            throw new ValidationException("Node choice required but no nodes chosen").
                    set("DEFINITION_NODE", definitionNode).
                    set("OBJECT_NODE", objectNode);
        }
    }

    private String print(Set<String> unrecognizedNodes) {
        StringBuilder string = new StringBuilder();
        for (String node : unrecognizedNodes) {
            string.append(node);
            string.append(", ");
        }
        return string.toString();
    }
}
