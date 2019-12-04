import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import unidadeNegocios, {
  UnidadeNegociosState
} from 'app/entities/unidade-negocios/unidade-negocios.reducer';
// prettier-ignore
import feriados, {
  FeriadosState
} from 'app/entities/feriados/feriados.reducer';
// prettier-ignore
import departamentos, {
  DepartamentosState
} from 'app/entities/departamentos/departamentos.reducer';
// prettier-ignore
import equipes, {
  EquipesState
} from 'app/entities/equipes/equipes.reducer';
// prettier-ignore
import cargos, {
  CargosState
} from 'app/entities/cargos/cargos.reducer';
// prettier-ignore
import turnos, {
  TurnosState
} from 'app/entities/turnos/turnos.reducer';
// prettier-ignore
import colaboradores, {
  ColaboradoresState
} from 'app/entities/colaboradores/colaboradores.reducer';
// prettier-ignore
import arquivos, {
  ArquivosState
} from 'app/entities/arquivos/arquivos.reducer';
// prettier-ignore
import linhasArquivos, {
  LinhasArquivosState
} from 'app/entities/linhas-arquivos/linhas-arquivos.reducer';
// prettier-ignore
import controlePonto, {
  ControlePontoState
} from 'app/entities/controle-ponto/controle-ponto.reducer';
// prettier-ignore
import ponto, {
  PontoState
} from 'app/entities/ponto/ponto.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly unidadeNegocios: UnidadeNegociosState;
  readonly feriados: FeriadosState;
  readonly departamentos: DepartamentosState;
  readonly equipes: EquipesState;
  readonly cargos: CargosState;
  readonly turnos: TurnosState;
  readonly colaboradores: ColaboradoresState;
  readonly arquivos: ArquivosState;
  readonly linhasArquivos: LinhasArquivosState;
  readonly controlePonto: ControlePontoState;
  readonly ponto: PontoState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  unidadeNegocios,
  feriados,
  departamentos,
  equipes,
  cargos,
  turnos,
  colaboradores,
  arquivos,
  linhasArquivos,
  controlePonto,
  ponto,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
