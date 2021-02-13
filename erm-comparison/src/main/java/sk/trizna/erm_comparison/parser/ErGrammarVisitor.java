/*
 * Copyright shanki. All rights reserved.
 */
package sk.trizna.erm_comparison.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;

import grammar.ErGrammarBaseVisitor;
import grammar.ErGrammarParser;
import grammar.ErGrammarParser.AttributeContext;
import grammar.ErGrammarParser.SideContext;
import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.utils.ParseUtils;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.GeneralizationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;


/**
 *
 * @author shanki, Adam Trizna
 */
public class ErGrammarVisitor extends ErGrammarBaseVisitor<Object> {

    private final Map<String,EntitySet> storedEntities = new HashMap<>();
    private final ERModel model = new ERModel();

    void add(EntitySet entitySet) {
        if (entitySet == null) {
            throw new NullPointerException("entity cannot be null");
        }

        storedEntities.put(entitySet.getNameText(),entitySet);
        model.addEntitySet(entitySet);
    }

    void add(Relationship relationship) {
        if (relationship == null) {
            throw new NullPointerException("relationship cannot be null");
        }

        model.addRelationship(relationship);
    }

    public ERModel transform(ParseTree tree) {
        visit(tree);

        return model;
    }

    @Override
    public String visitText(ErGrammarParser.TextContext ctx) {
        return (String) super.visitText(ctx);
    }

    @Override
    public String visitText_id(ErGrammarParser.Text_idContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitText_string(ErGrammarParser.Text_stringContext ctx) {
        return removeQuotes(ctx.value.getText());
    }

    private String removeQuotes(String string) {
        return string.substring(1, string.length() - 1);
    }

    @Override
    public Number visitNumber(ErGrammarParser.NumberContext ctx) {
        return (Number) super.visitNumber(ctx);
    }

    @Override
    public Integer visitNumber_integer(ErGrammarParser.Number_integerContext ctx) {
        return Integer.valueOf(ctx.value.getText());
    }

    @Override
    public Double visitNumber_decimal(ErGrammarParser.Number_decimalContext ctx) {
        return Double.valueOf(ctx.value.getText());
    }

    @Override
    public String visitAttribute(AttributeContext ctx) {
        return visitText(ctx.value);
    }

    @Override
    public Void visitEntity_set_definition(ErGrammarParser.Entity_set_definitionContext ctx) {

        String name = visitText(ctx.name);
        EntitySet entitySet = new EntitySet(name);

        for (AttributeContext attributeCtx : ctx.attribute()) {
            entitySet.addAttribute(new Attribute(visitAttribute(attributeCtx)));
        }

        add(entitySet);
        
        return null;
    }

    @Override
    public AssociationSide visitSide(ErGrammarParser.SideContext ctx) {
        String partName = visitText(ctx.part);
        EntitySet part = storedEntities.get(partName);

        EnumRelationshipSideRole cardinality = null;
        if (ctx.CARDINALITY() != null) {
            cardinality = ParseUtils.parseCardinality(ctx.CARDINALITY().getText());
        }
        
        return new AssociationSide(part,cardinality);
    }
    
    @Override
    public Void visitAssociation_definition(ErGrammarParser.Association_definitionContext ctx) {
        String name = null;
        if (ctx.name != null) {
            name = visitText(ctx.name);
        }
        
        Association association = new Association();
        association.setNameText(name);
        
        for (SideContext sideContext : ctx.side()) {
            association.getSides().add(visitSide(sideContext));
            
        }
       
        for (AttributeContext attributeCtx : ctx.attribute()) {
            association.addAttribute(new Attribute(visitAttribute(attributeCtx)));
        }
        
        model.addRelationship(association);
        return null;
    }

    @Override
    public Void visitGeneralization_definition(ErGrammarParser.Generalization_definitionContext ctx) {
        String name = null;
        String from = null;
        String to = null;
        
        if (ctx.name != null) {
            name = visitText(ctx.name);
        }
        if (ctx.from != null) {
            from = visitText(ctx.from);
        }
        if (ctx.to != null) {
            to = visitText(ctx.to);
        }
        
        GeneralizationSide superSide = new GeneralizationSide(storedEntities.get(to),EnumRelationshipSideRole.SUPER);
        GeneralizationSide subSide = new GeneralizationSide(storedEntities.get(from),EnumRelationshipSideRole.SUB);
                
        if (superSide.getEntitySet() == null) throw new NullPointerException(to + " doesn't exist");
        if (subSide.getEntitySet() == null) throw new NullPointerException(from + " doesn't exist");
        
        Generalization generalization = new Generalization(Arrays.asList(superSide,subSide));
        generalization.setNameText(name);
        
        model.addRelationship(generalization);
        return null;
    }  
}
