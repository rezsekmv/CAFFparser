/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UserDto } from '../models/UserDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class AuthService {

    /**
     * Új felhasználó létrehozása
     * @param requestBody 
     * @returns UserDto OK
     * @throws ApiError
     */
    public static signUp(
requestBody: UserDto,
): CancelablePromise<UserDto> {
        return __request({
            method: 'POST',
            path: `/api/public/sign-up`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Access token frissítése
     * @returns string OK
     * @throws ApiError
     */
    public static refreshToken(): CancelablePromise<string> {
        return __request({
            method: 'GET',
            path: `/api/refresh-token`,
        });
    }

}