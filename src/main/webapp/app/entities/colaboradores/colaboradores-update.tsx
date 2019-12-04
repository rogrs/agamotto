import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './colaboradores.reducer';
import { IColaboradores } from 'app/shared/model/colaboradores.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IColaboradoresUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IColaboradoresUpdateState {
  isNew: boolean;
}

export class ColaboradoresUpdate extends React.Component<IColaboradoresUpdateProps, IColaboradoresUpdateState> {
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
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { colaboradoresEntity } = this.props;
      const entity = {
        ...colaboradoresEntity,
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
    this.props.history.push('/colaboradores');
  };

  render() {
    const { colaboradoresEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="agamottoApp.colaboradores.home.createOrEditLabel">
              <Translate contentKey="agamottoApp.colaboradores.home.createOrEditLabel">Create or edit a Colaboradores</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : colaboradoresEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="colaboradores-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="colaboradores-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomeLabel" for="colaboradores-nome">
                    <Translate contentKey="agamottoApp.colaboradores.nome">Nome</Translate>
                  </Label>
                  <AvField
                    id="colaboradores-nome"
                    type="text"
                    name="nome"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="matriculaLabel" for="colaboradores-matricula">
                    <Translate contentKey="agamottoApp.colaboradores.matricula">Matricula</Translate>
                  </Label>
                  <AvField
                    id="colaboradores-matricula"
                    type="text"
                    name="matricula"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="pisLabel" for="colaboradores-pis">
                    <Translate contentKey="agamottoApp.colaboradores.pis">Pis</Translate>
                  </Label>
                  <AvField
                    id="colaboradores-pis"
                    type="text"
                    name="pis"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="sexoLabel" for="colaboradores-sexo">
                    <Translate contentKey="agamottoApp.colaboradores.sexo">Sexo</Translate>
                  </Label>
                  <AvInput
                    id="colaboradores-sexo"
                    type="select"
                    className="form-control"
                    name="sexo"
                    value={(!isNew && colaboradoresEntity.sexo) || 'FEMININO'}
                  >
                    <option value="FEMININO">{translate('agamottoApp.TipoSexo.FEMININO')}</option>
                    <option value="MASCULINO">{translate('agamottoApp.TipoSexo.MASCULINO')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="admissaoLabel" for="colaboradores-admissao">
                    <Translate contentKey="agamottoApp.colaboradores.admissao">Admissao</Translate>
                  </Label>
                  <AvField
                    id="colaboradores-admissao"
                    type="date"
                    className="form-control"
                    name="admissao"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="cpfLabel" for="colaboradores-cpf">
                    <Translate contentKey="agamottoApp.colaboradores.cpf">Cpf</Translate>
                  </Label>
                  <AvField id="colaboradores-cpf" type="text" name="cpf" />
                </AvGroup>
                <AvGroup>
                  <Label id="ciLabel" for="colaboradores-ci">
                    <Translate contentKey="agamottoApp.colaboradores.ci">Ci</Translate>
                  </Label>
                  <AvField id="colaboradores-ci" type="text" name="ci" />
                </AvGroup>
                <AvGroup>
                  <Label id="demissaoLabel" for="colaboradores-demissao">
                    <Translate contentKey="agamottoApp.colaboradores.demissao">Demissao</Translate>
                  </Label>
                  <AvField id="colaboradores-demissao" type="date" className="form-control" name="demissao" />
                </AvGroup>
                <AvGroup>
                  <Label id="atualizadoLabel" for="colaboradores-atualizado">
                    <Translate contentKey="agamottoApp.colaboradores.atualizado">Atualizado</Translate>
                  </Label>
                  <AvField id="colaboradores-atualizado" type="date" className="form-control" name="atualizado" />
                </AvGroup>
                <AvGroup>
                  <Label id="criacaoLabel" for="colaboradores-criacao">
                    <Translate contentKey="agamottoApp.colaboradores.criacao">Criacao</Translate>
                  </Label>
                  <AvField id="colaboradores-criacao" type="date" className="form-control" name="criacao" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/colaboradores" replace color="info">
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
  colaboradoresEntity: storeState.colaboradores.entity,
  loading: storeState.colaboradores.loading,
  updating: storeState.colaboradores.updating,
  updateSuccess: storeState.colaboradores.updateSuccess
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
)(ColaboradoresUpdate);
