import {User} from "./user.model";

export interface FinanceRecord{
  id: number;
  amount: number;
  createdDate: number;
  type: number;
  user: User;
}
