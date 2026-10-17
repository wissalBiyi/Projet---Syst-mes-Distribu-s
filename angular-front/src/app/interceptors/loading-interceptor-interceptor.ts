import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from '@angular/core';
import {Loading} from '../services/loading';
import {finalize} from 'rxjs';

export const loadingInterceptorInterceptor: HttpInterceptorFn = (req, next) => {

  const loadingService = inject(Loading);
  loadingService.setLoading(true);
  return next(req).pipe(finalize (()=>{
    loadingService.setLoading(false);
  }));
};
