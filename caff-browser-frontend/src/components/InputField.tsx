import Color from '../styles/Color';

interface InputFieldParams {
  inputType: string;
  placeholder: string;
  classes: string
  handleOnChange: Function;
}

const InputField = ({
  inputType,
  placeholder,
  classes,
  handleOnChange,
}: InputFieldParams) => {

  return (
    <div className={classes || ''}>
      <input
        type={inputType || 'text'}
        onChange={(e) => {
          handleOnChange(e.target.value);
        }}
        style={inputStyle}
        placeholder={placeholder}
      ></input>
    </div>
  );
};

const inputStyle = {
  backgroundColor: Color.white,
  padding: '.5rem ',
  margin: '.7rem .3rem .2rem .3rem',
  width: '100%',
};

export default InputField;
