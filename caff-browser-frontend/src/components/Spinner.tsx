import Loader from 'react-loader-spinner';
import Color from '../styles/Color';

const Spinner = () => {
  return (
    <div className="spinner-bg">
      <div className="spinner">
        <Loader type="TailSpin" color={Color.red} height={150} width={150} />
      </div>
    </div>
  );
};

export default Spinner;
