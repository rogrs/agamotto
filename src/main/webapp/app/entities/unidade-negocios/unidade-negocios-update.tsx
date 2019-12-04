import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './unidade-negocios.reducer';
import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUnidadeNegociosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUnidadeNegociosUpdateState {
  isNew: boolean;
}

export class UnidadeNegociosUpdate extends React.Component<IUnidadeNegociosUpdateProps, IUnidadeNegociosUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { unidadeNegociosEntity } = this.props;
      const entity = {
        ...unidadeNegociosEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/unidade-negocios');
  };

  render() {
    const { unidadeNegociosEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="agamottoApp.unidadeNegocios.home.createOrEditLabel">
              <Translate contentKey="agamottoApp.unidadeNegocios.home.createOrEditLabel">Create or edit a UnidadeNegocios</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : unidadeNegociosEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="unidade-negocios-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="unidade-negocios-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="razaoSocialLabel" for="unidade-negocios-razaoSocial">
                    <Translate contentKey="agamottoApp.unidadeNegocios.razaoSocial">Razao Social</Translate>
                  </Label>
                  <AvField
                    id="unidade-negocios-razaoSocial"
                    type="text"
                    name="razaoSocial"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nomeEmpresaLabel" for="unidade-negocios-nomeEmpresa">
                    <Translate contentKey="agamottoApp.unidadeNegocios.nomeEmpresa">Nome Empresa</Translate>
                  </Label>
                  <AvField
                    id="unidade-negocios-nomeEmpresa"
                    type="text"
                    name="nomeEmpresa"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="cnpjLabel" for="unidade-negocios-cnpj">
                    <Translate contentKey="agamottoApp.unidadeNegocios.cnpj">Cnpj</Translate>
                  </Label>
                  <AvField id="unidade-negocios-cnpj" type="text" name="cnpj" />
                </AvGroup>
                <AvGroup>
                  <Label id="inscricaoEstadualLabel" for="unidade-negocios-inscricaoEstadual">
                    <Translate contentKey="agamottoApp.unidadeNegocios.inscricaoEstadual">Inscricao Estadual</Translate>
                  </Label>
                  <AvField id="unidade-negocios-inscricaoEstadual" type="text" name="inscricaoEstadual" />
                </AvGroup>
                <AvGroup>
                  <Label id="empregadoraLabel" for="unidade-negocios-empregadora">
                    <Translate contentKey="agamottoApp.unidadeNegocios.empregadora">Empregadora</Translate>
                  </Label>
                  <AvInput
                    id="unidade-negocios-empregadora"
                    type="select"
                    className="form-control"
                    name="empregadora"
                    value={(!isNew && unidadeNegociosEntity.empregadora) || 'SIM'}
                  >
                    <option value="SIM">{translate('agamottoApp.TipoBoolean.SIM')}</option>
                    <option value="NAO">{translate('agamottoApp.TipoBoolean.NAO')}</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/unidade-negocios" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  unidadeNegociosEntity: storeState.unidadeNegocios.entity,
  loading: storeState.unidadeNegocios.loading,
  updating: storeState.unidadeNegocios.updating,
  updateSuccess: storeState.unidadeNegocios.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UnidadeNegociosUpdate);
