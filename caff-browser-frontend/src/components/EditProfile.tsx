import { Button, Form, FormControl, InputGroup } from "react-bootstrap";

const EditProfile = () => {

    const profileData = {
        id: 1,
        username: 'test_user',
        password: 'hash',
        email: 'testuser@example.com',
        displayName: 'Test user',
        roles: ['ADMINISTRATOR', 'MODERATOR']
    }

    return (
        <>
            <h3>Edit profile</h3>
            <div style={styles.box}>
                <h4>Profile data</h4>
                <div>
                    <Form.Label htmlFor="display-name">Display name</Form.Label>
                    <InputGroup>
                        <FormControl id="display-name" defaultValue={profileData.displayName}></FormControl>
                    </InputGroup>
                    <Form.Label htmlFor="email">Email</Form.Label>
                    <InputGroup>
                        <FormControl id="email" defaultValue={profileData.email}></FormControl>
                    </InputGroup>
                    <Button style={{ marginTop: 10 }} variant="primary">Save</Button>
                </div>
            </div>
        </>
    )
}

const styles = {
    box: {
        display: 'block',
        margin: 'auto',
        maxWidth: 600,
        marginTop: 40,
        boxShadow: '0px 0px 1.5px 1.5px rgba(0, 0, 0, 0.1)',
        borderRadius: 5,
        padding: 30
    }
}

export default EditProfile;