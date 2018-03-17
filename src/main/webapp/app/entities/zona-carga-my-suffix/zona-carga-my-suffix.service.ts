import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ZonaCargaMySuffix } from './zona-carga-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ZonaCargaMySuffix>;

@Injectable()
export class ZonaCargaMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/zona-cargas';

    constructor(private http: HttpClient) { }

    create(zonaCarga: ZonaCargaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(zonaCarga);
        return this.http.post<ZonaCargaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(zonaCarga: ZonaCargaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(zonaCarga);
        return this.http.put<ZonaCargaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ZonaCargaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ZonaCargaMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ZonaCargaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ZonaCargaMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ZonaCargaMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ZonaCargaMySuffix[]>): HttpResponse<ZonaCargaMySuffix[]> {
        const jsonResponse: ZonaCargaMySuffix[] = res.body;
        const body: ZonaCargaMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ZonaCargaMySuffix.
     */
    private convertItemFromServer(zonaCarga: ZonaCargaMySuffix): ZonaCargaMySuffix {
        const copy: ZonaCargaMySuffix = Object.assign({}, zonaCarga);
        return copy;
    }

    /**
     * Convert a ZonaCargaMySuffix to a JSON which can be sent to the server.
     */
    private convert(zonaCarga: ZonaCargaMySuffix): ZonaCargaMySuffix {
        const copy: ZonaCargaMySuffix = Object.assign({}, zonaCarga);
        return copy;
    }
}
