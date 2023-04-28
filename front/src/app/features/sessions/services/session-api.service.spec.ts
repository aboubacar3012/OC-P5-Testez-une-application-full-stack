import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Session} from "../interfaces/session.interface";

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [SessionApiService]
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  afterEach(() => {
    httpMock.verify();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('all', () => {
    it('should make a GET request to the sessions API', () => {
      const mockResponse: Session[] = [
        {
          id: 1,
          name: 'Mock Session',
          date: new Date(),
          teacher_id: 1,
          description: 'Mock description',
          users: []
        }
      ];

      service.all().subscribe((response: Session[]) => {
        expect(response).toEqual(mockResponse);
      });

      const mockRequest = httpMock.expectOne(service["pathService"]);
      expect(mockRequest.request.method).toBe('GET');
      mockRequest.flush(mockResponse);
    });
  });

  describe('detail', () => {
    it('should make a GET request to the session API with the given id', () => {
      const sessionId = '1';
      const mockResponse: Session[] = [
        {
          id: 1,
          name: 'Mock Session',
          date: new Date(),
          teacher_id: 1,
          description: 'Mock description',
          users: []
        }
      ];

      service.detail(sessionId).subscribe((response: Session) => {
        expect(response).toEqual(mockResponse);
      });

      const mockRequest = httpMock.expectOne(`${service["pathService"]}/${sessionId}`);
      expect(mockRequest.request.method).toBe('GET');
      mockRequest.flush(mockResponse);
    });
  });

  describe("delete", () => {
    it('should make a DELETE request to the session API with the given id ', () => {
      const sessionId = '1';
      const mockResponse: Session[] = [
        {
          id: 1,
          name: 'Mock Session',
          date: new Date(),
          teacher_id: 1,
          description: 'Mock description',
          users: []
        }
      ];
      service.delete(sessionId).subscribe((response: Session) => {
        expect(response).toEqual(mockResponse);
      });
      const mockRequest = httpMock.expectOne(`${service["pathService"]}/${sessionId}`);
      expect(mockRequest.request.method).toBe('DELETE');
      mockRequest.flush(mockResponse);
    })
  })

  describe('create', () => {
    it('should create a session', () => {
      const dummySession: Session = {
        id: 1,
        name: 'Mock Session',
        date: new Date(),
        teacher_id: 1,
        description: 'Mock description',
        users: []
      };

      service.create(dummySession).subscribe(session => {
        expect(session).toEqual(dummySession);
      });

      const request = httpMock.expectOne(`${service['pathService']}`);
      expect(request.request.method).toBe('POST');
      expect(request.request.body).toEqual(dummySession);
      request.flush(dummySession);
    });
  })

  describe('update', () => {
    it('should update a session', () => {
      const dummySession: Session = {
        id: 1,
        name: 'Mock Session',
        date: new Date(),
        teacher_id: 1,
        description: 'Mock description',
        users: []
      };
      const sessionId = '1';

      service.update(sessionId, dummySession).subscribe(session => {
        expect(session).toEqual(dummySession);
      });

      const request = httpMock.expectOne(`${service['pathService']}/${sessionId}`);
      expect(request.request.method).toBe('PUT');
      expect(request.request.body).toEqual(dummySession);
      request.flush(dummySession);
    });
  })

  describe('participate', () => {
    it('should participate in a session', () => {
      const sessionId = '1';
      const userId = '3';

      service.participate(sessionId, userId).subscribe(() => {
        expect.anything()
      });

      const req = httpMock.expectOne(`${service["pathService"]}/${sessionId}/participate/${userId}`);
      expect(req.request.method).toEqual('POST');
      req.flush(null);
    });
  })

  describe('unParticipate', () => {
    it('should unParticipate in a session', () => {
      const sessionId = '1';
      const userId = '3';

      service.unParticipate(sessionId, userId).subscribe(() => {
        expect.anything()
      });

      const req = httpMock.expectOne(`${service["pathService"]}/${sessionId}/participate/${userId}`);
      expect(req.request.method).toEqual('DELETE');
      req.flush(null);
    });
  })
});
