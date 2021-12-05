import { useEffect, useState } from 'react';
import { Alert, Button, Form, FormControl, InputGroup } from 'react-bootstrap';
import { UserDto, UserService } from '../services/openapi';

const EditProfile = () => {
  const [myUser, setMyUser] = useState<UserDto>({});
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    UserService.getMyUser()
      .then((me) => {
        setMyUser(me);
      })
      .catch((err) => {});
  }, []);

  const handleDataChange = (e: any) => {
    let tmpUser = { ...myUser };
    if (e.target.id === 'display-name') tmpUser.name = e.target.value;
    if (e.target.id === 'email') tmpUser.email = e.target.value;
    setMyUser(tmpUser);
  };

  const saveData = () => {
    UserService.updateMyUser(myUser)
      .then((res) => {
        setMyUser(myUser);
        setSuccess('Data was set successfully');
      })
      .catch((error) => {
        console.error(JSON.stringify(error));
        setError('Submit failed, some error happened');
      });
  };

  return (
    <>
      <h3>Edit profile</h3>
      <div style={styles.box}>
        <h4>Profile data</h4>
        <div>
          <Form.Label htmlFor="display-name">Display name</Form.Label>
          <InputGroup>
            <FormControl
              id="display-name"
              value={myUser?.name}
              onChange={(e) => {
                handleDataChange(e);
              }}
            ></FormControl>
          </InputGroup>
          <Form.Label htmlFor="email" className="mt-3">
            Email
          </Form.Label>
          <InputGroup>
            <FormControl
              id="email"
              value={myUser?.email}
              onChange={(e) => {
                handleDataChange(e);
              }}
            ></FormControl>
          </InputGroup>
          <Button className="my-3" variant="primary" onClick={() => saveData()}>
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
  box: {
    display: 'block',
    margin: 'auto',
    maxWidth: 600,
    marginTop: 40,
    boxShadow: '0px 0px 1.5px 1.5px rgba(0, 0, 0, 0.1)',
    borderRadius: 5,
    padding: 30,
  },
};

export default EditProfile;
