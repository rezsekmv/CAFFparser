/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PasswordDto } from '../models/PasswordDto';
import type { UserDto } from '../models/UserDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class UserService {

    /**
     * Saját felhasználó megtekintése
     * @returns UserDto OK
     * @throws ApiError
     */
    public static getMyUser(): CancelablePromise<UserDto> {
        return __request({
            method: 'GET',
            path: `/api/user`,
        });
    }

    /**
     * Saját felhasználó módosítása
     * @param requestBody 
     * @returns UserDto OK
     * @throws ApiError
     */
    public static updateMyUser(
requestBody: UserDto,
): CancelablePromise<UserDto> {
        return __request({
            method: 'PUT',
            path: `/api/user`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Saját felhasználó eltávolítása
     * @returns any OK
     * @throws ApiError
     */
    public static deleteMyUser(): CancelablePromise<any> {
        return __request({
            method: 'DELETE',
            path: `/api/user`,
        });
    }

    /**
     * Saját felhasználó jelszavának módosítása
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static updateMyPassword(
requestBody: PasswordDto,
): CancelablePromise<any> {
        return __request({
            method: 'PUT',
            path: `/api/user/password`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}