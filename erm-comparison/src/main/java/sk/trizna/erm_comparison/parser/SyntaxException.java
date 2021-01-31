/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.trizna.erm_comparison.parser;

import java.util.List;

import lombok.Getter;

/**
 *
 * @author shanki
 */
public class SyntaxException extends Exception {
     
	private static final long serialVersionUID = 8906049881238949098L;
	
	@Getter
    private final List<String> errors;

    SyntaxException(List<String> errors) {
        super(String.format("%d errors: %s", errors.size(), errors.toString()));

        this.errors = errors;
    }

}
