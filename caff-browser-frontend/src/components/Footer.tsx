import Color from '../styles/Color';

const Footer = () => {
  return (
    <div className="w-100 footer" style={footerStyle}>
      <div className='w-100' style={redLineStyle}></div>
      <div className='w-100' style={darkFooterStyle}>
        <span className='float-start' style={footerTextStyle}><a className='text-decoration-none' style={{color: Color.white}} href='https://www.youtube.com/watch?v=Dpz2ZCcO4OI'>Carnage Feat. Sludge - El Diablo Productions</a></span>
        <span className='float-end' style={footerTextStyle}><a className='text-decoration-none' style={{color: Color.white}} href='https://github.com/kulcsii/CAFFparser'>App information</a></span>
      </div>
    </div>
  );
};

const footerStyle = {
  left: '0',
  bottom: '0',
  witdh: '100%',
  marginTop: '20px'
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
