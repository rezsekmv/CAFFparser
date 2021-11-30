import Color from '../styles/Color';

const Footer = () => {
  return (
    <div className="w-100 footer" style={footerStyle}>
      <div className='w-100' style={redLineStyle}></div>
      <div className='w-100' style={darkFooterStyle}>
        <span className='float-start' style={footerTextStyle}>Carnage Feat. Sludge - El Diablo Productions</span>
        <span className='float-end' style={footerTextStyle}>App information</span>
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
  height: '8vh',
  paddingLeft: '10%',
  paddingRight: '10vw',
  paddingTop: '1.5vh'
};

const footerTextStyle = {
  fontSize: '2.5vh'
}

const redLineStyle = {
  height: '1vh',
  backgroundColor: Color.red,
};

export default Footer;
