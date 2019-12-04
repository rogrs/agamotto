import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IColaboradores } from 'app/shared/model/colaboradores.model';
import { getEntities as getColaboradores } from 'app/entities/colaboradores/colaboradores.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cargos.reducer';
import { ICargos } from 'app/shared/model/cargos.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICargosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICargosUpdateState {
  isNew: boolean;
  colaboradorId: string;
}

export class CargosUpdate extends React.Component<ICargosUpdateProps, ICargosUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      colaboradorId: '0',
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

    this.props.getColaboradores();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { cargosEntity } = this.props;
      const entity = {
        ...cargosEntity,
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
    this.props.history.push('/cargos');
  };

  render() {
    const { cargosEntity, colaboradores, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="agamottoApp.cargos.home.createOrEditLabel">
              <Translate contentKey="agamottoApp.cargos.home.createOrEditLabel">Create or edit a Cargos</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : cargosEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="cargos-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="cargos-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomeLabel" for="cargos-nome">
                    <Translate contentKey="agamottoApp.cargos.nome">Nome</Translate>
                  </Label>
                  <AvField
                    id="cargos-nome"
                    type="text"
                    name="nome"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="cargos-colaborador">
                    <Translate contentKey="agamottoApp.cargos.colaborador">Colaborador</Translate>
                  </Label>
                  <AvInput id="cargos-colaborador" type="select" className="form-control" name="colaborador.id">
                    <option value="" key="0" />
                    {colaboradores
                      ? colaboradores.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nome}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/cargos" replace color="info">
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
  colaboradores: storeState.colaboradores.entities,
  cargosEntity: storeState.cargos.entity,
  loading: storeState.cargos.loading,
  updating: storeState.cargos.updating,
  updateSuccess: storeState.cargos.updateSuccess
});

const mapDispatchToProps = {
  getColaboradores,
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
)(CargosUpdate);
