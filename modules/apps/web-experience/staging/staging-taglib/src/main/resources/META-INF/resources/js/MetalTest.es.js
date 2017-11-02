import Component from 'metal-component';
import Soy from 'metal-soy/src/Soy';
import templates from './MetalTest.soy';

class MetalTest extends Component {
    created() {
        console.info('created');
    }
}

// Register component
Soy.register(MetalTest, templates);

export default MetalTest;