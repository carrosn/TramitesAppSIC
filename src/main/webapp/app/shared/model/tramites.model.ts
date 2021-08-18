import { IPersona } from 'app/shared/model/persona.model';

export interface ITramites {
  id?: number;
  numeroTramite?: string;
  anno?: string;
  nombreTramite?: string;
  descripcion?: string;
  personaRadica?: string;
  funcionario?: string;
  radicaPersona?: IPersona;
  funcionarioPersona?: IPersona;
}

export class Tramites implements ITramites {
  constructor(
    public id?: number,
    public numeroTramite?: string,
    public anno?: string,
    public nombreTramite?: string,
    public descripcion?: string,
    public personaRadica?: string,
    public funcionario?: string,
    public radicaPersona?: IPersona,
    public funcionarioPersona?: IPersona
  ) {}
}
