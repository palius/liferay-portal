import Component from 'metal-component';
import Soy from 'metal-soy/src/Soy';
import templates from './AliusComp.soy';

class AliusComp extends Component {
    created() {
        console.info('created');
    }
}

// Register component
Soy.register(AliusComp, templates);

export default AliusComp;