import Component from "metal-component";
import Soy from "metal-soy";
import State from "metal-state";
import templates from "./StagedPortletDisplay.soy";

class StagedPortletDisplay extends Component {
	created() {
		const portletData = this.getState().portletData;
		console.log(portletData);
	}

    rendered() {
	        this.element.classList.remove("hide");
    }
}

StagedPortletDisplay.STATE = {
	portletData: {
		value: {}
	}
}

Soy.register(StagedPortletDisplay, templates);

export { StagedPortletDisplay };
export default StagedPortletDisplay;