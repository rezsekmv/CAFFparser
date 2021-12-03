import { useEffect, useState } from 'react';
import { Button, Form, FormControl, InputGroup } from 'react-bootstrap';
import { UserDto, UserService } from '../services/openapi';

const EditProfile = () => {
  const [myUser, setMyUser] = useState<UserDto>();

  useEffect(() => {
    UserService.getMyUser()
      .then((me) => {
        setMyUser(me);
      })
      .catch((err) => {});
  }, []);

  const handleDataChange = () => {
  }

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
              onChange={(e:any) => {
                  handleDataChange()
              }}
            ></FormControl>
          </InputGroup>
          <Form.Label htmlFor="email">Email</Form.Label>
          <InputGroup>
            <FormControl id="email" value={myUser?.email}></FormControl>
          </InputGroup>
          <Button style={{ marginTop: 10 }} variant="primary">
            Save
          </Button>
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
