// This code is taken from WSO2 Carbon and is licensed by WSO2, Inc.
// under the Apache License version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
package org.wso2.carbon.bpel.ui.bpel2svg.impl;

import org.wso2.carbon.bpel.ui.bpel2svg.BPEL2SVGFactory;
import org.wso2.carbon.bpel.ui.bpel2svg.PickInterface;
import org.wso2.carbon.bpel.ui.bpel2svg.ActivityInterface;
import org.apache.axiom.om.OMElement;

public class PickImpl extends FlowImpl implements PickInterface {

    public PickImpl(String token) {
        super(token);

        // Set Icon and Size
        startIconPath = org.wso2.carbon.bpel.ui.bpel2svg.BPEL2SVGFactory.getInstance().getIconPath(this.getClass().getName());
        endIconPath = org.wso2.carbon.bpel.ui.bpel2svg.BPEL2SVGFactory.getInstance().getEndIconPath(this.getClass().getName());
        // Set Layout
        setVerticalChildLayout(false);
    }

    public PickImpl(OMElement omElement) {
        super(omElement);

        // Set Icon and Size
        startIconPath = org.wso2.carbon.bpel.ui.bpel2svg.BPEL2SVGFactory.getInstance().getIconPath(this.getClass().getName());
        endIconPath = BPEL2SVGFactory.getInstance().getEndIconPath(this.getClass().getName());
        // Set Layout
        setVerticalChildLayout(false);
    }

    public PickImpl(OMElement omElement, ActivityInterface parent) {
        super(omElement);
        setParent(parent);
        // Set Icon and Size
        startIconPath = BPEL2SVGFactory.getInstance().getIconPath(this.getClass().getName());
        endIconPath = BPEL2SVGFactory.getInstance().getEndIconPath(this.getClass().getName());

        setVerticalChildLayout(false);
    }

    @Override
    public String getEndTag() {
        return BPEL2SVGFactory.PICK_END_TAG;
    }

}
