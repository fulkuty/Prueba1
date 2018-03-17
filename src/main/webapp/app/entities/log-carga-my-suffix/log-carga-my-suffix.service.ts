import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LogCargaMySuffix } from './log-carga-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LogCargaMySuffix>;

@Injectable()
export class LogCargaMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/log-cargas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(logCarga: LogCargaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(logCarga);
        return this.http.post<LogCargaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(logCarga: LogCargaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(logCarga);
        return this.http.put<LogCargaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LogCargaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LogCargaMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogCargaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogCargaMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LogCargaMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LogCargaMySuffix[]>): HttpResponse<LogCargaMySuffix[]> {
        const jsonResponse: LogCargaMySuffix[] = res.body;
        const body: LogCargaMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LogCargaMySuffix.
     */
    private convertItemFromServer(logCarga: LogCargaMySuffix): LogCargaMySuffix {
        const copy: LogCargaMySuffix = Object.assign({}, logCarga);
        copy.time = this.dateUtils
            .convertDateTimeFromServer(logCarga.time);
        copy.programado = this.dateUtils
            .convertDateTimeFromServer(logCarga.programado);
        copy.inicio = this.dateUtils
            .convertDateTimeFromServer(logCarga.inicio);
        copy.fin = this.dateUtils
            .convertDateTimeFromServer(logCarga.fin);
        return copy;
    }

    /**
     * Convert a LogCargaMySuffix to a JSON which can be sent to the server.
     */
    private convert(logCarga: LogCargaMySuffix): LogCargaMySuffix {
        const copy: LogCargaMySuffix = Object.assign({}, logCarga);

        copy.time = this.dateUtils.toDate(logCarga.time);

        copy.programado = this.dateUtils.toDate(logCarga.programado);

        copy.inicio = this.dateUtils.toDate(logCarga.inicio);

        copy.fin = this.dateUtils.toDate(logCarga.fin);
        return copy;
    }
}
