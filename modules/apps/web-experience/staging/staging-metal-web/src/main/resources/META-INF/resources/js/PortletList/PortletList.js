import Component from "metal-component";
import Soy from "metal-soy";
import State from "metal-state";
import Checkbox from "../components/checkbox/Checkbox";
import templates from "./PortletList.soy";

class PortletList extends Component {
    created() {
        console.info(this.props);
    }

    onClick() {
        this.setState({
            exportModelCount: "10"
        });
    }
}

PortletList.STATE = {
    exportModelCount: {
        value: "0"
    },
    modelDeletionCount: {
        value: "0"
    }
}

Soy.register(PortletList, templates);

export { PortletList };
export default PortletList;