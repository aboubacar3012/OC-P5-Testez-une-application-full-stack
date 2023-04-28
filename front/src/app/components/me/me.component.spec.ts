import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';

import { MeComponent } from './me.component';
import {UserService} from "../../services/user.service";
import {of} from "rxjs";
import {User} from "../../interfaces/user.interface";
import {Router} from "@angular/router";

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userServiceMock: jest.Mocked<UserService>
  let routerServiceMock: jest.Mocked<Router>;
  let matSnackBarServiceMock: jest.Mocked<MatSnackBar>;
  let sessionServiceMock: jest.Mocked<SessionService>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    } as User
  }
  beforeEach(async () => {
    const userMock = {getById: jest.fn(), delete: jest.fn()}
    const sessionMock = {logOut: jest.fn(), sessionInformation: {id: 1} as User}
    const routerMock = {navigate: jest.fn()}
    const matSnackBarMock = {open: jest.fn()} as unknown as MatSnackBar;

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: SessionService, useValue: sessionMock },
        { provide: UserService, useValue: userMock},
        { provide: Router, useValue: routerMock },
        { provide: MatSnackBar, useValue: matSnackBarMock }

      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    userServiceMock = TestBed.inject(UserService) as jest.Mocked<UserService>;
    sessionServiceMock = TestBed.inject(SessionService) as jest.Mocked<SessionService>;
    routerServiceMock = TestBed.inject(Router) as jest.Mocked<Router>;
    matSnackBarServiceMock = TestBed.inject(MatSnackBar) as jest.Mocked<MatSnackBar>
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get User Data', () => {
    userServiceMock.getById.mockReturnValue(of(mockSessionService.sessionInformation));
    fixture.detectChanges()
    expect(component.user).toEqual(mockSessionService.sessionInformation)
  })

  it('should delete the account and navigate to home page', () => {
    userServiceMock.delete.mockReturnValue(of(null));

    component.delete();
    expect(userServiceMock.delete).toHaveBeenCalledWith('1');
    expect(matSnackBarServiceMock.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 })
    expect(sessionServiceMock.logOut).toHaveBeenCalled();
    expect(routerServiceMock.navigate).toHaveBeenCalledWith(["/"])
  })

  // it('should handle error', () => {
  //   userServiceMock.delete.mockReturnValue(of(null).pipe(() => {
  //     throw new Error("Delete account failed")
  //   }))
  //   component.delete();
  //
  //   expect(userServiceMock.delete).toHaveBeenCalledWith('1');
  //   expect(matSnackBarServiceMock.open).toHaveBeenCalledWith("Delete account failed", 'Close', { duration: 3000 })
  //   expect(sessionServiceMock.logOut).not.toHaveBeenCalled();
  //   expect(routerServiceMock.navigate).not.toHaveBeenCalledWith(["/"])
  // })

  it('should call window.history.back on back()', () => {
    const spy = jest.spyOn(window.history, 'back').mockImplementation(() => {});
    component.back();
    expect(spy).toHaveBeenCalled();
  })
});
