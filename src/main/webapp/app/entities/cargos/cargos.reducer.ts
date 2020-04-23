import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICargos, defaultValue } from 'app/shared/model/cargos.model';

export const ACTION_TYPES = {
  SEARCH_CARGOS: 'cargos/SEARCH_CARGOS',
  FETCH_CARGOS_LIST: 'cargos/FETCH_CARGOS_LIST',
  FETCH_CARGOS: 'cargos/FETCH_CARGOS',
  CREATE_CARGOS: 'cargos/CREATE_CARGOS',
  UPDATE_CARGOS: 'cargos/UPDATE_CARGOS',
  DELETE_CARGOS: 'cargos/DELETE_CARGOS',
  RESET: 'cargos/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICargos>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CargosState = Readonly<typeof initialState>;

// Reducer

export default (state: CargosState = initialState, action): CargosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CARGOS):
    case REQUEST(ACTION_TYPES.FETCH_CARGOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CARGOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CARGOS):
    case REQUEST(ACTION_TYPES.UPDATE_CARGOS):
    case REQUEST(ACTION_TYPES.DELETE_CARGOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CARGOS):
    case FAILURE(ACTION_TYPES.FETCH_CARGOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CARGOS):
    case FAILURE(ACTION_TYPES.CREATE_CARGOS):
    case FAILURE(ACTION_TYPES.UPDATE_CARGOS):
    case FAILURE(ACTION_TYPES.DELETE_CARGOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CARGOS):
    case SUCCESS(ACTION_TYPES.FETCH_CARGOS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARGOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CARGOS):
    case SUCCESS(ACTION_TYPES.UPDATE_CARGOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CARGOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cargos';
const apiSearchUrl = 'api/_search/cargos';

// Actions

export const getSearchEntities: ICrudSearchAction<ICargos> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CARGOS,
  payload: axios.get<ICargos>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ICargos> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CARGOS_LIST,
  payload: axios.get<ICargos>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICargos> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CARGOS,
    payload: axios.get<ICargos>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICargos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CARGOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICargos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CARGOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICargos> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CARGOS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
