import Component from "metal-component";
import Soy from "metal-soy";
import State from "metal-state";
import templates from "./StagedPortletDisplay.soy";

class StagedPortletDisplay extends Component {
    onClick() {
        this.setState({
            exportModelCount: "10"
        });
    }

    rendered() {
        this.element.classList.remove("hide");
    }
}

StagedPortletDisplay.STATE = {
    exportModelCount: {
        value: "0"
    },
    modelDeletionCount: {
        value: "0"
    }
}

Soy.register(StagedPortletDisplay, templates);

export { StagedPortletDisplay };
export default StagedPortletDisplay;