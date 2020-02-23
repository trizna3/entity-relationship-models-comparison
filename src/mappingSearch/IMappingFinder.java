package mappingSearch;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

public interface IMappingFinder {

    /**
     * @param exemplarModel
     * @param studentModel
     * @return Mapping of entity sets of given models, which minimizes the penalty computed by penalty evaluators.
     */
    Mapping getBestMapping(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentModel);
}
