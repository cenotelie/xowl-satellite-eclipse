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

package org.xowl.satellites.eclipse.denotation.parsers;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.xowl.infra.denotation.phrases.Parser;
import org.xowl.infra.denotation.phrases.Phrase;
import org.xowl.infra.denotation.phrases.PhraseVocabulary;
import org.xowl.infra.denotation.phrases.Sign;
import org.xowl.infra.denotation.phrases.SignPropertyName;
import org.xowl.infra.denotation.phrases.SignPropertyPosition2D;
import org.xowl.infra.denotation.phrases.SignPropertyShape;
import org.xowl.infra.denotation.phrases.SignPropertySize2D;
import org.xowl.infra.denotation.phrases.SignPropertyZone2D;
import org.xowl.infra.denotation.phrases.SignRelation;
import org.xowl.satellites.eclipse.denotation.Constants;

/**
 * Implements a phrase parser for a GMF notation diagram
 * 
 * @author Laurent Wouters
 */
public class GMFDiagramParser implements Parser<Diagram> {
	@Override
	public String getIdentifier() {
		return GMFDiagramParser.class.getCanonicalName();
	}

	@Override
	public String getName() {
		return "xOWL Eclipse Satellite - GMF Notation Diagram parser";
	}

	@Override
	public PhraseVocabulary getVocabulary() {
		return PhraseVocabulary.REGISTER;
	}

	@Override
	public Phrase parse(Diagram diagram) {
		Map<Object, Sign> signsMap = new HashMap<>();
		onFindDiagram(diagram, signsMap);
		List<Sign> signs = new ArrayList<>();
		for (Map.Entry<Object, Sign> entry : signsMap.entrySet()) {
			signs.add(entry.getValue());
			if (entry.getKey() instanceof Node)
				loadNode((Node) entry.getKey(), entry.getValue(), signsMap);
			else if (entry.getKey() instanceof Edge)
				loadEdge((Edge) entry.getKey(), entry.getValue(), signsMap);
		}
		return new Phrase(diagram.getName(), diagram.getName(), signs);
	}

	/**
	 * When on a diagram
	 * 
	 * @param diagram
	 *            The diagram
	 * @param signsMap
	 *            The map of known signs
	 */
	private void onFindDiagram(Diagram diagram, Map<Object, Sign> signsMap) {
		Sign sign = new Sign(EcoreUtil.getURI(diagram).toString(), diagram.getName());
		signsMap.put(diagram, sign);
		for (Object object : diagram.getPersistedChildren()) {
			Node node = (Node) object;
			onFindNode(node, sign, signsMap, 0, 0, 0, 0);
		}
		for (Object object : diagram.getPersistedEdges()) {
			Edge edge = (Edge) object;
			onFindEdge(edge, sign, signsMap);
		}
	}

	/**
	 * When on a node
	 * 
	 * @param node
	 *            The node
	 * @param signsMap
	 *            The map of known signs
	 * @param offsetX
	 *            The total offset of this node from the diagram boundary
	 * @param offsetY
	 *            The total offset of this node from the diagram boundary
	 * @param parentWidth
	 *            The width of the parent node
	 * @param parentHeight
	 *            The height of the parent node
	 */
	private void onFindNode(Node node, Sign parent, Map<Object, Sign> signsMap, int offsetX, int offsetY,
			int parentWidth, int parentHeight) {
		String identifier = EcoreUtil.getURI(node).toString();
		String name = getName(node.getElement());
		if (name == null)
			name = identifier;
		Sign sign = new Sign(identifier, name);
		signsMap.put(node, sign);

		int x = offsetX;
		int y = offsetY;
		int width = 0;
		int height = 0;
		LayoutConstraint layoutConstraint = node.getLayoutConstraint();
		if (layoutConstraint != null && layoutConstraint instanceof Bounds) {
			Bounds bounds = (Bounds) layoutConstraint;
			x += bounds.getX();
			y += bounds.getY();
			width = bounds.getWidth();
			height = bounds.getHeight();
			if (width == 0)
				width = parentWidth - bounds.getX();
			if (height == 0)
				height = parentHeight - bounds.getY();
		} else if (layoutConstraint != null && layoutConstraint instanceof Location) {
			Location location = (Location) layoutConstraint;
			x += location.getX();
			y += location.getY();
			width = parentWidth - location.getX();
			height = parentHeight - location.getY();
		} else {
			width = parentWidth;
			height = parentHeight;
		}

		sign.addPropertyValue(SignPropertyName.INSTANCE, name);
		sign.addPropertyValue(SignPropertyZone2D.INSTANCE, identifier);
		sign.addPropertyValue(SignPropertyPosition2D.INSTANCE, new Point2D.Double(x, y));
		sign.addPropertyValue(SignPropertySize2D.INSTANCE, new Point2D.Double(width, height));
		String shape = node.getType();
		if (shape != null)
			sign.addPropertyValue(SignPropertyShape.INSTANCE, shape);

		sign.addRelationSign(Constants.RELATION_IN_DIAGRAM, signsMap.get(node.getDiagram()));
		sign.addRelationSign(SignRelation.RELATION_CONTAINED_BY, parent);
		parent.addRelationSign(SignRelation.RELATION_CONTAINS, sign);

		for (Object object : node.getPersistedChildren()) {
			Node child = (Node) object;
			onFindNode(child, sign, signsMap, x, y, width, height);
		}
	}

	/**
	 * When on an edge
	 * 
	 * @param edge
	 *            The node
	 * @param signsMap
	 *            The map of known signs
	 */
	private void onFindEdge(Edge edge, Sign parent, Map<Object, Sign> signsMap) {
		String identifier = EcoreUtil.getURI(edge).toString();
		String name = getName(edge.getElement());
		if (name == null)
			name = identifier;
		Sign sign = new Sign(identifier, name);
		signsMap.put(edge, sign);

		Bendpoints bendpoints = edge.getBendpoints();
		if (bendpoints != null && bendpoints instanceof RelativeBendpoints) {
			RelativeBendpoints relativeBendpoints = (RelativeBendpoints) bendpoints;
			relativeBendpoints.getPoints();
		}

		sign.addPropertyValue(SignPropertyName.INSTANCE, name);
		sign.addPropertyValue(SignPropertyZone2D.INSTANCE, identifier);
		String shape = edge.getType();
		if (shape != null)
			sign.addPropertyValue(SignPropertyShape.INSTANCE, shape);

		sign.addRelationSign(Constants.RELATION_IN_DIAGRAM, signsMap.get(edge.getDiagram()));
		sign.addRelationSign(SignRelation.RELATION_CONTAINED_BY, parent);
		parent.addRelationSign(SignRelation.RELATION_CONTAINS, sign);

		for (Object object : edge.getPersistedChildren()) {
			Node child = (Node) object;
			onFindNode(child, sign, signsMap, 0, 0, 0, 0);
		}
	}

	/**
	 * Loads the definition of a node
	 * 
	 * @param node
	 *            The node
	 * @param sign
	 *            The associated sign
	 * @param signsMap
	 *            The map of known signs
	 */
	private void loadNode(Node node, Sign sign, Map<Object, Sign> signsMap) {

	}

	/**
	 * Loads the definition of a node
	 * 
	 * @param node
	 *            The node
	 * @param sign
	 *            The associated sign
	 * @param signsMap
	 *            The map of known signs
	 */
	private void loadEdge(Edge edge, Sign sign, Map<Object, Sign> signsMap) {
		Sign source = signsMap.get(edge.getSource());
		Sign target = signsMap.get(edge.getTarget());
		if (source != null)
			sign.addRelationSign(Constants.RELATION_FROM, source);
		if (target != null)
			sign.addRelationSign(Constants.RELATION_TO, target);
	}

	/**
	 * Tries to get the name if the specified object
	 * 
	 * @param object
	 *            An object
	 * @return
	 */
	private String getName(EObject object) {
		if (object == null)
			return null;
		for (EAttribute attribute : object.eClass().getEAllAttributes()) {
			if (attribute.getName().equals("name")) {
				Object value = object.eGet(attribute);
				if (value != null)
					return value.toString();
			} else if (attribute.getName().equals("label")) {
				Object value = object.eGet(attribute);
				if (value != null)
					return value.toString();
			} else if (attribute.getName().equals("title")) {
				Object value = object.eGet(attribute);
				if (value != null)
					return value.toString();
			}
		}
		return null;
	}
}
