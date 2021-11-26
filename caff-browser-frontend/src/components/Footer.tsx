import Color from '../styles/Color';

const Footer = () => {
  return (
    <div className="position-fixed w-100" style={footerStyle}>
      <div className='w-100' style={redLineStyle}></div>
      <div className='w-100' style={darkFooterStyle}>
          <span className='float-start'>Carnage Feat. Sludge - El Diablo Productions</span>
          <span className='float-end'>App information</span>
      </div>
    </div>
  );
};

const footerStyle = {
  left: '0',
  bottom: '0',
  witdh: '100%',
};

const darkFooterStyle = {
  backgroundColor: Color.dark,
  color: Color.white,
  height: '50px',
  paddingLeft: '10rem',
  paddingRight: '10rem',
  paddingTop: '.8rem'
};

const redLineStyle = {
  height: '10px',
  backgroundColor: Color.red,
};

export default Footer;
