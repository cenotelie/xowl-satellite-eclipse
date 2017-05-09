/*******************************************************************************
 * Copyright (c) 2017 Association Cénotélie (cenotelie.fr)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package org.xowl.satellites.eclipse.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.RGB;

import fr.cenotelie.hime.redist.ParseError;
import fr.cenotelie.hime.redist.TextSpan;
import fr.cenotelie.hime.redist.Token;
import fr.cenotelie.hime.redist.TokenRepository;
import fr.cenotelie.hime.redist.lexer.LexicalErrorHandler;

/**
 * A presentation repairer that uses Hime
 * 
 * @author Laurent Wouters
 */
public abstract class HimePresentationRepairer implements IPresentationRepairer, LexicalErrorHandler {
	/**
	 * The current document
	 */
	protected IDocument document;

	@Override
	public void setDocument(IDocument document) {
		this.document = document;
	}

	@Override
	public void createPresentation(TextPresentation presentation, ITypedRegion damage) {
		presentation.clear();
		TokenRepository tokens = doParse(document.get());
		if (tokens == null)
			return;
		for (Token token : tokens) {
			StyleRange styleRange = doStyle(token);
			if (styleRange != null)
				presentation.addStyleRange(styleRange);
		}
	}

	@Override
	public void handle(ParseError error) {
		// do nothing
	}

	/**
	 * Parses the specified input
	 * 
	 * @param input
	 *            The input to parse
	 * @return The token repository
	 */
	protected abstract TokenRepository doParse(String input);

	/**
	 * Gets the style for the specified token
	 * 
	 * @param token
	 *            A token
	 * @return The style, or null if no style applies
	 */
	protected abstract StyleRange doStyle(Token token);

	/**
	 * Creates a style range for the specified token
	 * 
	 * @param token
	 *            The token
	 * @param color
	 *            The color for the token
	 * @return The style range
	 */
	protected StyleRange doCreateStyle(Token token, RGB color) {
		TextSpan span = token.getSpan();
		return new StyleRange(span.getIndex(), span.getLength(), ColorManager.get().getColor(color), null);
	}

	/**
	 * Creates a style range for the specified token
	 * 
	 * @param token
	 *            The token
	 * @param color
	 *            The color for the token
	 * @return The style range
	 */
	protected StyleRange doCreateStyle(Token token, int color) {
		TextSpan span = token.getSpan();
		return new StyleRange(span.getIndex(), span.getLength(), ColorManager.get().getColor(color), null);
	}
}
