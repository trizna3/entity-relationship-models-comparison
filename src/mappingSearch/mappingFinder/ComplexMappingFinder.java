package mappingSearch.mappingFinder;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingEvaluator.BestMappingEvaluator;
import mappingSearch.mappingEvaluator.IBestMappingEvaluator;
import mappingSearch.mappingEvaluator.IMappingTransformationEvaluator;
import mappingSearch.mappingEvaluator.MappingTransformationEvaluator;
import transformations.types.Transformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author - Adam Trizna
 */

/**
 * Object for finding best mapping of given double of entity relationship models.
 * This version of finder does a simple full mapping search (not optimised in any way) for maximum clarity.
 * In addition to basic mapping finder, this finder uses model transformations for more effective mapping search.
 */
public class ComplexMappingFinder implements IMappingFinder {

    IBestMappingEvaluator bestMappingEvaluator;
    IMappingTransformationEvaluator mappingTransformationEvaluator;

    /**
     * Uses recursive backtrack algorithm to iterate over all possible mappings, compute their penalties, get the one with the lowest penalty.
     * @param exemplarModel
     * @param studentModel
     * @return Mapping of minimal penalty.
     */
    @Override
    public Mapping getBestMapping(ERModel exemplarModel, ERModel studentModel) {

        generateMappings(exemplarModel,studentModel,new Mapping(),0,new HashSet<>());
        return getBestMappingEvaluator().getBestMapping();
    }

    /**
     * Recursive backtrack algorithm for generating all possible mappings of entity sets of given models.
     * Once a mapping is complete, it's penalty is computed.
     * @param exemplarModel
     * @param studentModel
     * @param mapping
     */
    private void generateMappings(ERModel exemplarModel, ERModel studentModel, Mapping mapping, int exemplarEntitySetIndex, Set<EntitySet> usedStudentEntitySets) {

        // trivial case condition: if all exemplar entity sets are mapped, complete the mapping and send it for evaluation
        if (exemplarModel.getEntitySetsCount() <= exemplarEntitySetIndex) {
            completeMapping(studentModel,mapping);
//            getBestMappingEvaluator().evaluate(exemplarModel,studentModel,mapping);
            List<EntitySet> emptyEntitySets = getEmptyEntitySets(mapping.getDistinctAllEntitySets(true));
            getMappingTransformationEvaluator().getTransformationList(exemplarModel,studentModel,mapping);
            makeEntitySetsEmpty(emptyEntitySets);
            return;
        }

        EntitySet entitySetToBeMapped = exemplarModel.getEntitySets().get(exemplarEntitySetIndex);
        for (int i = 0; i < studentModel.getEntitySetsCount() + 1; i++) {
            EntitySet studentEntitySet;
            if (i == studentModel.getEntitySetsCount()) {
                studentEntitySet = Mapping.getEmptyEntitySet();
            } else {
                studentEntitySet = studentModel.getEntitySets().get(i);
            }
            if (usedStudentEntitySets.contains(studentEntitySet)) {
                continue;
            }

            // map
            mapping.map(entitySetToBeMapped,studentEntitySet);
            if (!studentEntitySet.equals(Mapping.getEmptyEntitySet())) {
                usedStudentEntitySets.add(studentEntitySet);
            }

            // dive
            generateMappings(exemplarModel,studentModel,mapping,exemplarEntitySetIndex+1,usedStudentEntitySets);

            // unmap
            mapping.unmap(entitySetToBeMapped);
            mapping.unmap(studentEntitySet);
            if (!studentEntitySet.equals(Mapping.getEmptyEntitySet())) {
                usedStudentEntitySets.remove(studentEntitySet);
            }
        }
    }

    private IBestMappingEvaluator getBestMappingEvaluator() {
        if (bestMappingEvaluator == null) {
            bestMappingEvaluator = new BestMappingEvaluator();
        }
        return bestMappingEvaluator;
    }

    public IMappingTransformationEvaluator getMappingTransformationEvaluator() {
        if (mappingTransformationEvaluator == null) {
            mappingTransformationEvaluator = new MappingTransformationEvaluator();
        }
        return mappingTransformationEvaluator;
    }

    /**
     * Once all exemplar entity sets are mapped, completes mapping, eg. maps all unmapped student's entity sets to an empty entity set.
     * @param studentModel
     * @param mapping
     */
    private void completeMapping(ERModel studentModel, Mapping mapping) {

        for (EntitySet entitySet : studentModel.getEntitySets()) {
            if (mapping.getImage(entitySet) == null) {
                mapping.map(entitySet,Mapping.getEmptyEntitySet());
            }
        }
    }

    private List<EntitySet> getEmptyEntitySets(Set<EntitySet> entitySets) {
        return entitySets.stream().filter(EntitySet::isEmpty).collect(Collectors.toList());
    }

    private void makeEntitySetsEmpty(List<EntitySet> entitySets) {
        for (EntitySet entitySet : entitySets) {
            entitySet.setEmpty(true);
        }
    }
}
