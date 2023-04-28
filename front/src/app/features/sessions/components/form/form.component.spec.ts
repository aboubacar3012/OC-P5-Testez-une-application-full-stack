import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import {TeacherService} from "../../../../services/teacher.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionInformation} from "../../../../interfaces/sessionInformation.interface";
import {Location} from "@angular/common";
import {ListComponent} from "../list/list.component";
import {of} from "rxjs";
import {Session} from "../../interfaces/session.interface";

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let router:Router;

  // let teacherServiceMock: jest.Mocked<TeacherService>;
  let routerServiceMock: jest.Mocked<Router>
  // let sessionApiServiceMock: jest.Mocked<SessionApiService>;
  let sessionServiceMock: jest.Mocked<SessionService>;
  // let activatedRouteMock: jest.Mocked<ActivatedRoute>;

  //  sessionServiceMock = {
  //   sessionInformation: {
  //     admin: true
  //   }
  // }

  beforeEach(async () => {
    const routerMock = {navigate: jest.fn()}

    // sessionApiServiceMock = {
    //   detail: jest.fn(),
    //   create: jest.fn(),
    //   update: jest.fn()
    // } as unknown as jest.Mocked<SessionApiService>;

    const sessionMock = {
      sessionInformation:{
        admin: true
      } as SessionInformation
    }

    // teacherServiceMock = {
    //   all: jest.fn()
    // } as unknown as jest.Mocked<TeacherService>;
    //
    // activatedRouteMock = {
    //   snapshot: {
    //     paramMap: {
    //       get: jest.fn()
    //     }
    //   }
    // } as unknown as jest.Mocked<ActivatedRoute>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: "sessions", component: ListComponent },
          { path: "**", redirectTo: "" }
        ]),
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      declarations: [FormComponent],
      providers: [
        // {provide: Router, useValue: routerMock },
        { provide: SessionService, useValue: sessionMock },
        // { provide: SessionApiService, useValue: sessionApiServiceMock },
        // { provide: TeacherService, useValue: teacherServiceMock },
        // { provide: ActivatedRoute, useValue: activatedRouteMock }
        // SessionApiService
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    // router.initialNavigation()
    sessionServiceMock = TestBed.inject(SessionService) as jest.Mocked<SessionService>;
    // routerServiceMock = TestBed.inject(Router) as jest.Mocked<Router>;

    fixture.detectChanges();
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to sessions when not admin', () => {
    if (sessionServiceMock.sessionInformation) {
      sessionServiceMock.sessionInformation.admin = false;
    }

    const routerNavigateSpy = jest
      .spyOn(router, 'navigate')
      .mockResolvedValue(true)

    component.ngOnInit();
    expect(routerNavigateSpy).toHaveBeenCalledWith(['/sessions']);
  });


  // it('should set onUpdate flag and call sessionApiService.detail() when route contains "update"', () => {
  //   const session: Session = { id: 1, name: 'Session 1', date: new Date() };
  //   const detailSpy = jest.spyOn(sessionApiService, 'detail').mockReturnValue(of(session));
  // })

});
