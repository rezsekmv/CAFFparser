/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UserDto = {
    id?: number;
    username?: string;
    password?: string;
    email?: string;
    name?: string;
    roles?: Array<'ADMINISTRATOR' | 'MODERATOR'>;
}