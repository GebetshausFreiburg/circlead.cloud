/****************************************************************************
 **
 ** This demo file is part of yFiles for Java 3.1.0.1.
 **
 ** Copyright (c) 2000-2018 by yWorks GmbH, Vor dem Kreuzberg 28,
 ** 72070 Tuebingen, Germany. All rights reserved.
 **
 ** yFiles demo files exhibit yFiles for Java functionalities. Any redistribution
 ** of demo files in source code or binary form, with or without
 ** modification, is not permitted.
 **
 ** Owners of a valid software license for a yFiles for Java version that this
 ** demo is shipped with are allowed to use the demo source code as basis
 ** for their own yFiles for Java powered applications. Use of such programs is
 ** governed by the rights and conditions as set out in the yFiles for Java
 ** license agreement.
 **
 ** THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESS OR IMPLIED
 ** WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 ** MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
 ** NO EVENT SHALL yWorks BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 ** SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 ** TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 ** PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 ** LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 ** NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 ** SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **
 ***************************************************************************/
package org.rogatio.circlead.view.items.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.yworks.yfiles.geometry.IOrientedRectangle;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.styles.AbstractLabelStyle;
import com.yworks.yfiles.view.ICanvasContext;
import com.yworks.yfiles.view.IRenderContext;
import com.yworks.yfiles.view.IVisual;
import com.yworks.yfiles.view.Pen;

/**
 * A simple implementation of an {@link com.yworks.yfiles.graph.styles.ILabelStyle} that uses the convenience class {@link
 * com.yworks.yfiles.graph.styles.AbstractLabelStyle} as the base class.
 * The font for the label text can be set. The label text is drawn with black letters inside a blue rounded rectangle.
 * Also there is a customized button displayed in the label at certain zoom levels that enables editing of the label
 * text.
 */
public class SmallLabelStyle extends AbstractLabelStyle {
  
  /** The Constant HORIZONTAL_INSET. */
  private static final int HORIZONTAL_INSET = 0;
  
  /** The Constant VERTICAL_INSET. */
  private static final int VERTICAL_INSET = 0;
  
  /** The Constant BUTTON_SIZE. */
  private static final int BUTTON_SIZE = 8;

  /** The font. */
  private Font font;

  /**
   * Initializes a new <code>MySimpleLabelStyle</code> instance and sets a default font for the label.
   */
  public SmallLabelStyle() {
    font = new Font("Dialog", Font.PLAIN, 4);
  }

  /**
   * Gets the font used for rendering the label text.
   *
   * @return the font
   */
  public Font getFont() {
    return font;
  }

  /**
   * Sets the font used for rendering the label text.
   *
   * @param font the new font
   */
  public void setFont(Font font) {
    this.font = font;
  }

  /**
   * Creates the visual for a label to be drawn.
   *
   * @param context the context
   * @param label the label
   * @return the i visual
   */
  @Override
  protected IVisual createVisual(IRenderContext context, ILabel label) {

    // we need to arrange the label according to its layout. We use the dedicated method
    // AbstractLabelStyle#createLayoutTransform for this, which creates an AffineTransformation
    // that can be used to arrange an element according to a given IOrientedRectangle.
    AffineTransform layoutTransform = createLayoutTransform(label.getLayout(), true);

    LabelVisual visual = new LabelVisual();
    visual.update(label.getLayout(), label.getText(), getFont(), layoutTransform, isButtonVisible(context));
    return visual;
  }

  /**
   * Re-renders the node using the old visual instead of creating a new one for each call. It is strongly recommended to
   * do it for performance reasons. Otherwise, the {@link #createVisual(IRenderContext, ILabel)} is called instead.
   *
   * @param context the context
   * @param oldVisual the old visual
   * @param label the label
   * @return the i visual
   */
  @Override
  protected IVisual updateVisual(IRenderContext context, IVisual oldVisual, ILabel label) {
    LabelVisual visual = (LabelVisual) oldVisual;
    visual.update(label.getLayout(), label.getText(), getFont(), createLayoutTransform(label.getLayout(), true), isButtonVisible(context));
    return oldVisual;
  }

  /**
   * Determines whether the button is visible or not. The button is visible for zoom levels greater than or equal to 1.
   *
   * @param context the context
   * @return true, if is button visible
   */
  private static boolean isButtonVisible(ICanvasContext context) {
    return context.getZoom() >= 1;
  }

  /**
   * Calculates the preferred size for the given label if this style is used for the rendering.
   * The size is calculated from the label's text.
   *
   * @param label the label
   * @return the preferred size
   */
  @Override
  protected SizeD getPreferredSize(ILabel label) {
    // return size of the text plus some space for the button
    String text = label.getText();

    if (!text.isEmpty()) {
      // calculate the bounds of the text with the given font
      FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
      TextLayout textLayout = new TextLayout(text, font, frc);
      double textWidth = textLayout.getBounds().getWidth();
      double maxTextHeight = textLayout.getAscent() + textLayout.getDescent();

      // then use the desired size - plus rounding and insets
      return new SizeD(
          Math.ceil(0.5d + textWidth) + BUTTON_SIZE + 3 * HORIZONTAL_INSET,
          Math.max(Math.ceil(0.5d + maxTextHeight), BUTTON_SIZE) + 2 * VERTICAL_INSET);
    } else {
      return new SizeD(50 + BUTTON_SIZE + 3 * HORIZONTAL_INSET, BUTTON_SIZE + 2 * VERTICAL_INSET);
    }
  }

  /**
   * Provides an instance of {@link SmallLabelStyle.simplecustomstyle.MySimpleLabelStyle.EditActionButtonProvider}.
   *
   * @param label the label
   * @param type the type
   * @return the object
   */
  @Override
  protected Object lookup(ILabel label, Class type) {
      return super.lookup(label, type);
  }

  /**
   * A {@link IVisual} that paints a label with text and a button to open the label editor. Note
   * that we paint the label at the origin and move and rotate the graphics context to the current location and
   * orientation of the label. We store the background shape, the text, the position and the size of the label and the
   * visibility of the button as instance variables. The update method checks whether these values has been changed. If
   * so, the instance variables are updated.
   */
  private static class LabelVisual implements IVisual {
    
    /** The Constant FILL_COLOR. */
    // color to fill the background of the label with
    private static final Color FILL_COLOR = new Color(255,255,255,0);
    
    /** The Constant BORDER_PEN. */
    // the pen to draw the border of the label with
    private static final Pen BORDER_PEN = Pen.getTransparent();
    
    /** The background. */
    // the shape to draw the background of the label with
    private final RoundRectangle2D background;
    
    /** The text position. */
    // the position of the text within the label
    private PointD textPosition;

    /** The font. */
    // the font to the render the text with
    private Font font;

    /** The transform. */
    // the transformation that is used to arrange the label
    private AffineTransform transform;

    /** The label layout. */
    // the size and position of the label
    private IOrientedRectangle labelLayout;
    
    /** The label text. */
    // the text the label shows
    private String labelText;
 
    /**
     * Instantiates a new label visual.
     */
    LabelVisual() {
      background = new RoundRectangle2D.Double();
      textPosition = PointD.ORIGIN;
    }

    /**
     * Checks if the properties of the label have been changed. If so, updates all items needed to paint the label.
     * @param layout        the location and size of the label.
     * @param text          the text the label shows
     * @param font          the font to the render the text with
     * @param transform     the transform that arranges of the label
     * @param buttonVisible flag that specifies whether the button is visible or not
     */
    public void update(IOrientedRectangle layout, String text, Font font, AffineTransform transform, boolean buttonVisible) {

      if (!equals(layout, this.labelLayout) || !text.equals(this.labelText) ||
          !font.equals(this.font) || !this.transform.equals(transform)) {
        this.labelLayout = layout;
        this.labelText = text;
        this.font = font;
        this.transform = transform;
      
        // update background shape
        background.setRoundRect(0, 0, labelLayout.getWidth(), labelLayout.getHeight(), labelLayout.getWidth() / 10,
            labelLayout.getHeight() / 10);

        // calculate the position of the text
        if (!text.isEmpty()) {
          // calculate the bounds of the text with the given font
          FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
          TextLayout textLayout = new TextLayout(text, font, frc);
          Rectangle2D textBounds = textLayout.getBounds();

          // update text position, if edit button is visible align left, otherwise center
          double textX = buttonVisible ? HORIZONTAL_INSET : (labelLayout.getWidth() - textBounds.getWidth()) * 0.5;
          double textY = (labelLayout.getHeight() + textLayout.getAscent() - textLayout.getDescent()) * 0.5;
          textPosition = new PointD(textX, textY);
          // update button position
          double buttonX = labelLayout.getWidth() - HORIZONTAL_INSET - BUTTON_SIZE;
          double buttonY = VERTICAL_INSET;
        }
      }
    }

    /**
     * Checks two IOrientedRectangles for extensional equality.
     *
     * @param orientedRectangle1 the oriented rectangle 1
     * @param orientedRectangle2 the oriented rectangle 2
     * @return true, if successful
     */
    private boolean equals(IOrientedRectangle orientedRectangle1, IOrientedRectangle orientedRectangle2){
      if (orientedRectangle1 == orientedRectangle2){
        return true;
      }
      return orientedRectangle1 != null && orientedRectangle2 != null
          && orientedRectangle1.getAnchorX() == orientedRectangle2.getAnchorX()
          && orientedRectangle1.getAnchorY() == orientedRectangle2.getAnchorY()
          && orientedRectangle1.getWidth() == orientedRectangle2.getWidth()
          && orientedRectangle1.getHeight() == orientedRectangle2.getHeight()
          && orientedRectangle1.getUpX() == orientedRectangle2.getUpX()
          && orientedRectangle1.getUpY() == orientedRectangle2.getUpY();
    }
    
    /* (non-Javadoc)
     * @see com.yworks.yfiles.view.IVisual#paint(com.yworks.yfiles.view.IRenderContext, java.awt.Graphics2D)
     */
    @Override
    public void paint(IRenderContext context, Graphics2D g) {
      // All paint methods must maintain the state of the graphics context.
      // To do this, work on a copy of the graphic context.
      Graphics2D gfx = (Graphics2D) g.create();
      try {
        // move and rotate the graphics context to the current location and orientation of the label
        gfx.transform(transform);

        // paint the background of the label
        gfx.setPaint(FILL_COLOR);
        gfx.fill(background);
        BORDER_PEN.adopt(gfx);
        gfx.draw(background);

        // draw the text of the label in the center of the label's bounds
        if (!labelText.isEmpty()) {
          gfx.setColor(Color.BLACK);
          gfx.setFont(font);
          gfx.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
          gfx.drawString(labelText, (float) textPosition.getX(), (float) textPosition.getY());
        }

      } finally {
        // after all is done, dispose the copy
        gfx.dispose();
      }
    }
  }

}

