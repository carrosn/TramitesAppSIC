export interface INivelSatisfacion {
  id?: number;
  codigo?: string;
  descripcion?: string;
}

export class NivelSatisfacion implements INivelSatisfacion {
  constructor(public id?: number, public codigo?: string, public descripcion?: string) {}
}
