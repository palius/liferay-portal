import templates from './StagingRest.soy';
import Component from 'metal-component';
import Ajax from 'metal-ajax';
import Soy from 'metal-soy';

class StagingRest extends Component {
    created() {
        console.info("created");
    }
}

StagingRest.STATE = {
    
};

Soy.register(StagingRest, templates);

export { StagingRest };
export default StagingRest;