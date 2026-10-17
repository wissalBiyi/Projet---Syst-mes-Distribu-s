export interface Account{
  id?:string,
  balance?:number,
  createdAt?:string,
  type?:string
  customerId?:number
}
export enum RequestStatus {
  SUCCESS = 0,ERROR
}
export interface AccountListState{
  accounts?:Account[]
  statut?:RequestStatus,
  errorMessage?:string
}
