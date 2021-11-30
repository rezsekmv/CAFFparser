import { Button, FormControl, InputGroup } from "react-bootstrap";

const Profile = () => {

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
            <h3>My profile</h3>
            <div style={styles.profileBox}>
                <h4>Profile data</h4>
                <div className="profile-box-data">
                    <p><b>Username: </b> {profileData.username}</p>
                    <p><b>Email: </b> {profileData.email}</p>
                    <p><b>Display name: </b> {profileData.displayName}</p>
                    <p><b>My roles: </b> {profileData.roles.join(', ')}</p>
                    <Button style={{ marginTop: 10 }} variant="primary" href="/profile/edit">Modify</Button>
                </div>
            </div>
            <div style={styles.profileBox}>
                <div className="profile-box-data">
                    <p><h4>Modify password</h4></p>
                    <InputGroup>
                        <FormControl placeholder="Current password"></FormControl>
                    </InputGroup>
                    <InputGroup>
                        <FormControl placeholder="New password"></FormControl>
                    </InputGroup>
                    <InputGroup>
                        <FormControl placeholder="Repeat new password"></FormControl>
                    </InputGroup>
                    <Button style={{ marginTop: 10 }} variant="primary">Save</Button>
                </div>
            </div >
        </>
    )
}

const styles = {
    profileBox: {
        display: 'block',
        margin: 'auto',
        maxWidth: 600,
        marginTop: 40,
        boxShadow: '0px 0px 1.5px 1.5px rgba(0, 0, 0, 0.1)',
        borderRadius: 5,
        padding: 30
    }
}

export default Profile;