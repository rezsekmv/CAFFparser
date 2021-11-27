/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Pageable } from './Pageable';
import type { Sort } from './Sort';
import type { UserDto } from './UserDto';

export type PageUserDto = {
    totalPages?: number;
    totalElements?: number;
    number?: number;
    size?: number;
    numberOfElements?: number;
    content?: Array<UserDto>;
    sort?: Sort;
    first?: boolean;
    last?: boolean;
    pageable?: Pageable;
    empty?: boolean;
}