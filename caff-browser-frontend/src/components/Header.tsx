import Color from '../styles/Color';
import { Link, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';

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
      setLabel('My profile');
      setLink('/profile');
    }
  }, [link, label, location.pathname]);

  return (
    <div className="masthead">
      <div style={headerStyle}>
        <Link to={'/'} className="header-title">
          <span className="h1 float-start" style={titleStyle}>
            CAFF BROWSER
          </span>
        </Link>
        <div className="float-end" style={leftContainerStyle}>
          <Link className="text-decoration-none" style={linkStyle} to={link}>
            <span className="h2" style={headerTextStyle}>
              {label}
            </span>
          </Link>
          <Link
            className="text-decoration-none"
            style={linkStyle}
            to={'/logout'}
          >
            <span className="h2" style={headerTextStyle}>
              Logout
            </span>
          </Link>
        </div>
      </div>
      <div style={redLineStyle}></div>
    </div>
  );
};

const headerStyle = {
  backgroundColor: Color.dark,
  height: '8vh',
  paddingLeft: '10vw',
  paddingRight: '10vw',
};

const titleStyle = {
  color: Color.red,
  display: 'inline',
  fontSize: '4vh',
  marginTop: '1.5vh',
};

const linkStyle = {
  color: Color.white,
  fontSize: '3vh',
  marginTop: '1.5vh',
  marginRight: '2vw',
};

const headerTextStyle = {
  fontSize: '3vh',
  marginTop: '2vh',
};

const redLineStyle = {
  height: '1vh',
  backgroundColor: Color.red,
};

const leftContainerStyle = {
  paddingTop: '1.5vh',
  marginTop: 0,
  height: '8vh',
};

export default Header;
