import Component from "metal-component";
import Soy from "metal-soy";
import State from "metal-state";
import templates from "./Checkbox.soy";

class Checkbox extends Component {}

Soy.register(Checkbox, templates);

export { Checkbox };
export default Checkbox;