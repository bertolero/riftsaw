// This code is taken from WSO2 Carbon and is licensed by WSO2, Inc.
// under the Apache License version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
package org.wso2.carbon.bpel.ui.bpel2svg.impl;

import org.wso2.carbon.bpel.ui.bpel2svg.ActivityInterface;
import org.wso2.carbon.bpel.ui.bpel2svg.BPEL2SVGFactory;
import org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates;
import org.wso2.carbon.bpel.ui.bpel2svg.SVGDimension;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.apache.axiom.om.OMElement;


public class ForEachImpl extends ActivityImpl implements org.wso2.carbon.bpel.ui.bpel2svg.ForEachInterface {
    public ForEachImpl(String token) {
        super(token);

        // Set Icon and Size
        startIconPath = BPEL2SVGFactory.getInstance().getIconPath(this.getClass().getName());
        endIconPath = BPEL2SVGFactory.getInstance().getEndIconPath(this.getClass().getName());
        // Set Layout
        // setVerticalChildLayout(false);
    }

    public ForEachImpl(OMElement omElement) {
        super(omElement);

        // Set Icon and Size
        startIconPath = BPEL2SVGFactory.getInstance().getIconPath(this.getClass().getName());
        endIconPath = BPEL2SVGFactory.getInstance().getEndIconPath(this.getClass().getName());
        // Set Layout
        // setVerticalChildLayout(false);
    }

    public ForEachImpl(OMElement omElement, ActivityInterface parent) {
        super(omElement);
        setParent(parent);
        // Set Icon and Size
        startIconPath = BPEL2SVGFactory.getInstance().getIconPath(this.getClass().getName());
        endIconPath = BPEL2SVGFactory.getInstance().getEndIconPath(this.getClass().getName());
    }

    @Override
    public String getId() {
        return getName() + "-ForEach";
    }

    @Override
    public String getEndTag() {
        return BPEL2SVGFactory.FOREACH_END_TAG;
    }

    @Override
    public SVGDimension getDimensions() {
        if (dimensions == null) {
            int width = 0;
            int height = 0;
            dimensions = new SVGDimension(width, height);

            SVGDimension subActivityDim = null;
            ActivityInterface activity = null;
            Iterator<ActivityInterface> itr = getSubActivities().iterator();
            while (itr.hasNext()) {
                activity = itr.next();
                subActivityDim = activity.getDimensions();
                if (subActivityDim.getWidth() > width) {
                    width += subActivityDim.getWidth();
                }
                height += subActivityDim.getHeight();
            }

            height += ((getYSpacing() * 2) + getStartIconHeight() + getEndIconHeight());
            width += getXSpacing();

            dimensions.setWidth(width);
            dimensions.setHeight(height);
        }
        return dimensions;
    }

    @Override
    public void layout(int startXLeft, int startYTop) {
        if (layoutManager.isVerticalLayout()) {
            layoutVertical(startXLeft, startYTop);
        } else {
            layoutHorizontal(startXLeft, startYTop);
        }
    }

    public void layoutVertical(int startXLeft, int startYTop) {
        int centreOfMyLayout = startXLeft + (dimensions.getWidth() / 2);
        int xLeft = centreOfMyLayout - (getStartIconWidth() / 2);
        int yTop = startYTop + (getYSpacing() / 2);
        int endXLeft = centreOfMyLayout - (getEndIconWidth() / 2);
        int endYTop = startYTop + dimensions.getHeight() - getEndIconHeight() - (getYSpacing() / 2);

        ActivityInterface activity = null;
        Iterator<org.wso2.carbon.bpel.ui.bpel2svg.ActivityInterface> itr = getSubActivities().iterator();
        int childYTop = yTop + getStartIconHeight() + (getYSpacing() / 2);
        int childXLeft = startXLeft + (getXSpacing() / 2);
        while (itr.hasNext()) {
            activity = itr.next();
            //childXLeft = centreOfMyLayout - activity.getDimensions().getWidth() / 2;
            activity.layout(childXLeft, childYTop);
            childYTop += activity.getDimensions().getHeight();
        }

        // Set the values
        setStartIconXLeft(xLeft);
        setStartIconYTop(yTop);
        setEndIconXLeft(endXLeft);
        setEndIconYTop(endYTop);
        setStartIconTextXLeft(startXLeft + BOX_MARGIN);
        setStartIconTextYTop(startYTop + BOX_MARGIN + BPEL2SVGFactory.TEXT_ADJUST);
        getDimensions().setXLeft(startXLeft);
        getDimensions().setYTop(startYTop);
    }

    private void layoutHorizontal(int startXLeft, int startYTop) {
        int centreOfMyLayout = startYTop + (dimensions.getHeight() / 2);
        int xLeft = startXLeft + (getYSpacing() / 2);
        int yTop = centreOfMyLayout - (getStartIconHeight() / 2);
        int endXLeft = startXLeft + dimensions.getWidth() - getEndIconWidth() - (getYSpacing() / 2);
        int endYTop = centreOfMyLayout - (getEndIconHeight() / 2);

        ActivityInterface activity = null;
        Iterator<ActivityInterface> itr = getSubActivities().iterator();
        int childXLeft = xLeft + getStartIconWidth() + (getYSpacing() / 2);
        int childYTop = startYTop + (getXSpacing() / 2);
        while (itr.hasNext()) {
            activity = itr.next();
            //childYTop += centreOfMyLayout - (activity.getDimensions().getHeight() / 2);
            activity.layout(childXLeft, childYTop);
            childXLeft += activity.getDimensions().getWidth();
        }

        // Set the values
        setStartIconXLeft(xLeft);
        setStartIconYTop(yTop);
        setEndIconXLeft(endXLeft);
        setEndIconYTop(endYTop);
        setStartIconTextXLeft(startXLeft + BOX_MARGIN);
        setStartIconTextYTop(startYTop + BOX_MARGIN + org.wso2.carbon.bpel.ui.bpel2svg.BPEL2SVGFactory.TEXT_ADJUST);
        getDimensions().setXLeft(startXLeft);
        getDimensions().setYTop(startYTop);
    }

    @Override
    public SVGCoordinates getEntryArrowCoords() {
        int xLeft = 0;
        int yTop = 0;
        if (layoutManager.isVerticalLayout()) {
            xLeft = getStartIconXLeft() + (getStartIconWidth() / 2);
            yTop = getStartIconYTop();
        } else {
            xLeft = getStartIconXLeft();
            yTop = getStartIconYTop() + (getStartIconHeight() / 2);

        }

        SVGCoordinates coords = new SVGCoordinates(xLeft, yTop);

        return coords;
    }

    @Override
    public org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates getExitArrowCoords() {
        int xLeft = 0;
        int yTop = 0;
        if (layoutManager.isVerticalLayout()) {
            xLeft = getEndIconXLeft() + (getEndIconWidth() / 2);
            yTop = getEndIconYTop() + getEndIconHeight();
        } else {
            xLeft = getEndIconXLeft() + getEndIconWidth();
            yTop = getEndIconYTop() + (getEndIconHeight() / 2);

        }

        org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates coords = new SVGCoordinates(xLeft, yTop);

        return coords;
    }

    protected SVGCoordinates getStartIconExitArrowCoords() {
        int xLeft = 0;
        int yTop = 0;
        if (layoutManager.isVerticalLayout()) {
            xLeft = getStartIconXLeft() + (getStartIconWidth() / 2);
            yTop = getStartIconYTop() + getStartIconHeight();
        } else {
            xLeft = getStartIconXLeft() + getStartIconWidth();
            yTop = getStartIconYTop() + (getStartIconHeight() / 2);

        }

        SVGCoordinates coords = new org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates(xLeft, yTop);

        return coords;
    }

    protected org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates getEndIconEntryArrowCoords() {
        int xLeft = 0;
        int yTop = 0;
        if (layoutManager.isVerticalLayout()) {
            xLeft = getEndIconXLeft() + (getEndIconWidth() / 2);
            yTop = getEndIconYTop();
        } else {
            xLeft = getEndIconXLeft();
            yTop = getEndIconYTop() + (getEndIconHeight() / 2);

        }

        org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates coords = new SVGCoordinates(xLeft, yTop);

        return coords;
    }

    @Override
    public Element getSVGString(SVGDocument doc) {
        Element group = null;
        group = doc.createElementNS("http://www.w3.org/2000/svg", "g");
        group.setAttributeNS(null, "id", getLayerId());
        if (isAddOpacity()) {
            group.setAttributeNS(null, "style", "opacity:" + getOpacity());
        }
        group.appendChild(getBoxDefinition(doc));
        group.appendChild(getImageDefinition(doc));
        group.appendChild(getStartImageText(doc));
        // Process Sub Activities
        group.appendChild(getSubActivitiesSVGString(doc));
        group.appendChild(getEndImageDefinition(doc));
        //Add Arrow
        group.appendChild(getArrows(doc));

        return group;
    }

    protected Element getArrows(SVGDocument doc) {
        Element subGroup = null;
        subGroup = doc.createElementNS("http://www.w3.org/2000/svg", "g");
        if (subActivities != null) {
            ActivityInterface activity = null;
            String id = null;
            org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates myStartCoords = getStartIconExitArrowCoords();
            org.wso2.carbon.bpel.ui.bpel2svg.SVGCoordinates myExitCoords = getEndIconEntryArrowCoords();
            SVGCoordinates activityExitCoords = null;
            SVGCoordinates activityEntryCoords = null;
            Iterator<ActivityInterface> itr = subActivities.iterator();
            while (itr.hasNext()) {
                activity = itr.next();
                activityExitCoords = activity.getExitArrowCoords();
                activityEntryCoords = activity.getEntryArrowCoords();
                subGroup.appendChild(getArrowDefinition(doc, myStartCoords.getXLeft(),
                		myStartCoords.getYTop(), activityEntryCoords.getXLeft(), 
                		activityEntryCoords.getYTop(), id, activity, activity));
                subGroup.appendChild(getArrowDefinition(doc, activityExitCoords.getXLeft(), 
                		activityExitCoords.getYTop(), myExitCoords.getXLeft(),
                		myExitCoords.getYTop(), id, activity, activity));
            }
        }
        return subGroup;
    }

    @Override
    public boolean isAddOpacity() {
        return isAddCompositeActivityOpacity();
    }

    @Override
    public double getOpacity() {
        return getCompositeOpacity();
    }

}