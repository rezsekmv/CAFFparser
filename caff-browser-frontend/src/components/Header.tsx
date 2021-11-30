import Color from '../styles/Color';
import {useLocation} from 'react-router-dom';
import {useEffect, useState} from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faUserCircle} from '@fortawesome/fontawesome-free-solid';
import {IconProp} from '@fortawesome/fontawesome-svg-core';

const Header = (props: any) => {
    const location = useLocation();
    const [link, setLink] = useState('/');
    const [label, setLabel] = useState('');

    useEffect(() => {
        TokenService.saveAccessToken('asdasd');
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
                            <div className={'float-start'}>
                                <span className='h2' style={headerTextStyle}>{label}</span>
                            </div>
                            <div className={'float-end'}>
                                <FontAwesomeIcon style={iconStyle} icon={faUserCircleIcon} size='2x'></FontAwesomeIcon>
                            </div>
                        </a>
                    </div>
                    <div className="row">
                        <a className="text-decoration-none" style={linkStyle} href={link}>
                            <span className='h4' style={headerTextStyle}>My profile</span>
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
    height: '8vh',
    paddingLeft: '10vw',
    paddingRight: '10vw'
};

const titleStyle = {
    color: Color.red,
    display: 'inline',
    fontSize: '4vh',
    marginTop: '1.5vh'
};

const linkStyle = {
    color: Color.white,
    fontSize: '3vh'
}

const headerTextStyle = {
    fontSize: '3vh',
    marginTop: '2vh'
}

const iconStyle = {
    margin: '2vh'
}

const redLineStyle = {
    height: '1vh',
    backgroundColor: Color.red,
};

export default Header;
