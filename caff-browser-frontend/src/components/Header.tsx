import Color from '../styles/Color';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserCircle } from '@fortawesome/fontawesome-free-solid';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

const Header = (props: any) => {
  const location = useLocation();
  const [link, setLink] = useState('/');
  const [label, setLabel] = useState('');

  useEffect(() => {
    if (location.pathname === '/register') {
      setLabel('Bejelentkezés');
      setLink('/login');
    }
    if (location.pathname === '/login') {
      setLabel('Regisztráció');
      setLink('/register');
    }
    if (location.pathname === '/') {
      setLabel('BROWSE');
      setLink('/login');
    }
  }, [link, label, location.pathname]);

  const faUserCircleIcon = faUserCircle as IconProp;

  return (
    <>
      <div style={headerStyle}>
        <span className="h1 float-start" style={titleStyle}>
          CAFF BROWSER
        </span>
        <a className="text-decoration-none float-end" style={linkStyle} href={link}>
          {/*ts-ignore*/}
          <span className='h2'>{label}</span><FontAwesomeIcon style={iconStyle} icon={faUserCircleIcon} size='2x'></FontAwesomeIcon>
        </a>
      </div>
      <div style={redLineStyle}></div>
    </>
  );
};

const headerStyle = {
  backgroundColor: Color.dark,
  height: '70px',
  paddingLeft: '20px',
  paddingRight: '20px'
};

const titleStyle = {
  color: Color.red,
  display: 'inline',
};

const linkStyle = {
  color: Color.white
}

const iconStyle = {
  margin: '10px'
}

const redLineStyle = {
  height: '15px',
  backgroundColor: Color.red,
};

export default Header;
