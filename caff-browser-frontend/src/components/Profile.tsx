import { useEffect, useState } from 'react';
import { Alert, Button, FormControl, InputGroup } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { UserDto, UserService } from '../services/openapi';

const Profile = () => {
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [oldPassword, setOldPassword] = useState('');
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');
  const [myUser, setMyUser] = useState<UserDto>({
    id: 1,
    username: 'test_user',
    password: 'hash',
    email: 'testuser@example.com',
    name: 'Test user',
    roles: ['ADMINISTRATOR', 'MODERATOR'],
  });

  useEffect(() => {
    UserService.getMyUser().then((me) => {
      setMyUser(me);
    });
  }, []);

  const handleChangePassword = () => {
    if (newPassword !== confirmPassword) {
      setError('A két jelszó nem egyezik');
      return;
    }
    UserService.updateMyPassword({ oldPassword, newPassword })
      .then((res) => {
        setSuccess('Sikeres adatmódosítás');
      })
      .catch((err) => {
        setError('Sikertelen adatmódosítás');
      })
      .finally(() => {
        setTimeout(() => {
          setError('');
          setSuccess('');
        }, 2000);
      });
  };

  return (
    <>
      <h3>My profile</h3>
      <div style={styles.profileBox}>
        <h4>Profile data</h4>
        <div className="profile-box-data">
          <p>
            <b>Username: </b> {myUser.username}
          </p>
          <p>
            <b>Email: </b> {myUser.email}
          </p>
          <p>
            <b>Display name: </b> {myUser.name}
          </p>
          <p>
            <b>My roles: </b> {myUser.roles?.join(', ')}
          </p>
          <Button style={{ marginTop: 10 }} variant="primary">
            <Link to={'/profile/edit'}>Modify</Link>
          </Button>
        </div>
      </div>
      <div style={styles.profileBox}>
        <div className="profile-box-data">
          <p>
            <h4>Modify password</h4>
          </p>
          <InputGroup>
            <FormControl
              value={oldPassword}
              onChange={(e: any) => setOldPassword(e.target.value)}
              placeholder="Current password"
              type="password"
            ></FormControl>
          </InputGroup>
          <InputGroup>
            <FormControl
              value={newPassword}
              onChange={(e: any) => setNewPassword(e.target.value)}
              placeholder="New password"
              type="password"
            ></FormControl>
          </InputGroup>
          <InputGroup>
            <FormControl
              value={confirmPassword}
              onChange={(e: any) => setConfirmPassword(e.target.value)}
              placeholder="Repeat new password"
              type="password"
            ></FormControl>
          </InputGroup>
          <Button
            style={{ marginTop: 10, marginBottom: 20 }}
            onClick={() => handleChangePassword()}
            variant="primary"
          >
            Save
          </Button>
          {success && <Alert variant={'success'}>Sikeres adatmódosítás!</Alert>}
          {error && <Alert variant={'danger'}>Sikertelen adatmódosítás!</Alert>}
        </div>
      </div>
    </>
  );
};

const styles = {
  profileBox: {
    display: 'block',
    margin: 'auto',
    maxWidth: 600,
    marginTop: 40,
    boxShadow: '0px 0px 1.5px 1.5px rgba(0, 0, 0, 0.1)',
    borderRadius: 5,
    padding: 30,
  },
};

export default Profile;
