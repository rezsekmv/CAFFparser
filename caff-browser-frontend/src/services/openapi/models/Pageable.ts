/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Sort } from './Sort';

export type Pageable = {
    paged?: boolean;
    unpaged?: boolean;
    pageNumber?: number;
    pageSize?: number;
    offset?: number;
    sort?: Sort;
}