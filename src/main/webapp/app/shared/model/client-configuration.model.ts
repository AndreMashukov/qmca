export interface IClientConfiguration {
  id?: number;
  clientId?: number;
  attachment?: string;
  preTableNotificationTemplate?: string;
  postTableTemplate?: string;
}

export class ClientConfiguration implements IClientConfiguration {
  constructor(
    public id?: number,
    public clientId?: number,
    public attachment?: string,
    public preTableNotificationTemplate?: string,
    public postTableTemplate?: string
  ) {}
}
