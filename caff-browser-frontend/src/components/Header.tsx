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
    <div className="masthead">
      <div style={headerStyle}>
        <a href="/" className="header-title">
          <span className="h1 float-start" style={titleStyle}>
            CAFF BROWSER
          </span>
        </a>
        <div className="float-end">
          <div className="row">
            <a className="text-decoration-none" style={linkStyle} href={link}>
              <span className='h2'>{label}</span><FontAwesomeIcon style={iconStyle} icon={faUserCircleIcon} size='2x'></FontAwesomeIcon>
            </a>
          </div>
          <div className="row">
            <a className="text-decoration-none" style={linkStyle} href='/profile'>
              <span className='h4'>My profile</span>
            </a>
          </div>
        </div>
      </div>
      <div style={redLineStyle}></div>
    </div>
  );
};

const headerStyle = {
  backgroundColor: Color.dark,
  height: '70px',
  paddingLeft: '10rem',
  paddingRight: '10rem'
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
