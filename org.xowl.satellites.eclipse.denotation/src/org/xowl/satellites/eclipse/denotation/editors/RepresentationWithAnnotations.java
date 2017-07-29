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

package org.xowl.satellites.eclipse.denotation.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.SVGImage;
import org.eclipse.gmf.runtime.draw2d.ui.render.internal.factory.RenderedImageKey;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.xowl.infra.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class RepresentationWithAnnotations extends ScrolledComposite {
    /**
     * Initializes this representation
     *
     * @param parent The parent component
     * @param file   The representation file
     */
    public RepresentationWithAnnotations(Composite parent, IFile file) {
        super(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

        Image image = getImage(file);
        Rectangle rectangle = image.getBounds();

        Label label = new Label(this, SWT.CENTER);
        label.setImage(image);
        label.setSize(rectangle.width, rectangle.height);

        this.setContent(label);
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setMinWidth(rectangle.width);
        this.setMinHeight(rectangle.height);
    }

    /**
     * Gets the image from the file
     *
     * @param file The representation file
     * @return The image
     */
    private static Image getImage(IFile file) {
        if ("svg".equalsIgnoreCase(file.getFileExtension()))
            return getImageSvg(file);
        return getImageOther(file);
    }

    /**
     * Gets the image for the SVG file
     *
     * @param file The SVG representation file
     * @return The image
     */
    @SuppressWarnings("restriction")
    private static Image getImageSvg(IFile file) {
        try (InputStream stream = file.getContents()) {
            RenderedImageKey key = new RenderedImageKey();
            key.setValues(500, 500, true, true, new RGB(255, 255, 255), new RGB(0, 0, 0));
            byte[] buffer = IOUtils.load(stream);
            SVGImage image = new SVGImage(buffer, key);
            return image.getSWTImage();
        } catch (IOException | CoreException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the image from the file
     *
     * @param file The representation file
     * @return The image
     */
    private static Image getImageOther(IFile file) {
        try (InputStream stream = file.getContents()) {
            Image image = new Image(Display.getCurrent(), file.getContents());
            return image;
        } catch (IOException | CoreException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
