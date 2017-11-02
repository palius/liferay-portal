import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';
import templates from './metalTest.soy';

class MetalTest extends Component {
    created() {
        console.info('created');
    }
}

// Register component
Soy.register(MetalTest, templates);

export default MetalTest;